package com.premiumgrocery.freshandfast.ui.shop

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.premiumgrocery.freshandfast.Const
import com.premiumgrocery.freshandfast.local.IUserOrderRepository
import com.premiumgrocery.freshandfast.local.model.LocalCategoryData
import com.premiumgrocery.freshandfast.local.model.LocalSubcategoryData
import com.premiumgrocery.freshandfast.remote.ICategoryRepository
import com.premiumgrocery.freshandfast.remote.model.ProductData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.HashMap
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.forEach
import kotlin.collections.getOrPut
import kotlin.collections.hashMapOf
import kotlin.collections.listOf
import kotlin.collections.mutableListOf
import kotlin.collections.set
import kotlin.collections.toList

@HiltViewModel
class ShopViewModel @Inject constructor(
    private val categoryRepository: ICategoryRepository,
    private val userOrderRepository: IUserOrderRepository,
    private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    private val _processTasks = MutableStateFlow(listOf(Const.processLabelStart))
    val processTasks = _processTasks.asStateFlow()

    private val _categories = MutableLiveData<List<LocalCategoryData>>()
    val categories: LiveData<List<LocalCategoryData>> = _categories
    private val _subCategories = MutableLiveData<List<LocalSubcategoryData>>()
    val subCategories: LiveData<List<LocalSubcategoryData>> = _subCategories
    // key: Category ID, value: Subcategory
    // By the time categories is changed and observed, this variable should be initialized.
    lateinit var subcategoryReference: HashMap<Int, MutableList<LocalSubcategoryData>>
    private set

    private val _searchResult = MutableLiveData<List<ProductData>>()
    val searchResult: LiveData<List<ProductData>> = _searchResult

    // This is for sending to the repository when one datum is modified,
    //      and for the views to observe.
    // No the views cannot just observe currentOrders, since the data
    //      needs to be available on configuration change, and
    //      .collect isn't fast enough.
    // Be a good person and don't modify this hashmap manually.
    val currentOrdersStatic = hashMapOf<String, Int>()
    val currentOrders = getProductOrders().stateIn(viewModelScope, SharingStarted.Eagerly, hashMapOf())
    private val _orderTotalCost = MutableStateFlow(0.0)
    val orderTotalCost = _orderTotalCost.asStateFlow()

    init {
        viewModelScope.launch(ioDispatcher) {
            combine(
                categoryRepository.getGroceryCategories(),
                categoryRepository.getGrocerySubcategories()
            ) { category, subcategory -> Pair(category, subcategory) }
            .collect {
                // listOf() is necessary since system thinks emit() might not run.
                _categories.postValue(it.first ?: listOf())
                _subCategories.postValue(it.second ?: listOf())
                subcategoryReference =
                    hashMapOf<Int, MutableList<LocalSubcategoryData>>().apply {
                        it.second.forEach { s -> getOrPut(s.catId) { mutableListOf() }.add(s) }
                    }
            }
        }

        addProcessTask(Const.processLabelGetCurrentProducts)
        viewModelScope.launch(ioDispatcher) {
            currentOrders.collect {
                currentOrdersStatic.clear()
                currentOrdersStatic.putAll(it)
                // This ends the one from setProductOrder()
                endProcessTask(Const.processLabelGetCurrentProducts)
                updateTotalOrderCost()
            }
        }
    }

    private fun addProcessTask(taskName: String) {
        viewModelScope.launch{
            _processTasks.emit(_processTasks.value.plus(taskName))
        }
    }

    fun endProcessTask(taskName: String) {
        viewModelScope.launch{
            _processTasks.emit(_processTasks.value.minus(taskName))
        }
    }

    // If ur sure everything should be done, use this for safety measures.
    fun clearProcessTask() { viewModelScope.launch{ _processTasks.emit(listOf()) } }

    fun searchGroceryProduct(query: String) {
        addProcessTask(Const.processLabelSearchProduct)
        viewModelScope.launch(ioDispatcher) {
            categoryRepository.searchGroceryProduct(query)
                .collect{ _searchResult.postValue(it) }
        }.invokeOnCompletion { endProcessTask(Const.processLabelSearchProduct) }
    }

    fun searchGroceryProductByCategory(catId: Int) {
        addProcessTask(Const.processLabelSearchProduct)
        viewModelScope.launch(ioDispatcher) {
            categoryRepository.getGroceryProductByCategory(catId)
                .collect{ _searchResult.postValue(it) }
        }.invokeOnCompletion { endProcessTask(Const.processLabelSearchProduct) }
    }

    fun searchGroceryProductBySubcategory(subId: Int) {
        addProcessTask(Const.processLabelSearchProduct)
        viewModelScope.launch(ioDispatcher) {
            categoryRepository.getGroceryProductBySubcategory(subId)
                .collect{ _searchResult.postValue(it) }
        }.invokeOnCompletion { endProcessTask(Const.processLabelSearchProduct) }
    }

    fun getSuggestedProduct(query: String) {
        // rmb to take from local before going api.
    }

    // To remove an order, set the newAmount to 0.
    fun setProductOrder(productId: String, newAmount: Int) {
        currentOrdersStatic[productId] = newAmount
        addProcessTask(Const.processLabelGetCurrentProducts)
        viewModelScope.launch(ioDispatcher) {
            userOrderRepository.updateOrders(currentOrdersStatic)
        }
    }

    private fun getProductOrders() = userOrderRepository.getOrders()

    private fun updateTotalOrderCost() {
        addProcessTask(Const.processLabelGetTotalOrderCost)
        viewModelScope.launch(ioDispatcher) {
            categoryRepository.getGroceryProductByIds(
                currentOrdersStatic.keys.toList()
            ).collect{
                _orderTotalCost.emit(
                    userOrderRepository.updateTotalOrderCost( currentOrdersStatic, it )
                )
                endProcessTask(Const.processLabelGetTotalOrderCost)
            }
        }
    }
}