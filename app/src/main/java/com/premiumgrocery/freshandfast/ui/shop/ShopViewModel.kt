package com.premiumgrocery.freshandfast.ui.shop

import androidx.lifecycle.*
import com.premiumgrocery.freshandfast.local.model.LocalCategoryData
import com.premiumgrocery.freshandfast.local.model.LocalSubcategoryData
import com.premiumgrocery.freshandfast.remote.ICategoryRepository
import com.premiumgrocery.freshandfast.remote.model.SearchData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(
    private val categoryRepository: ICategoryRepository,
    private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    private val _categories = MutableLiveData<List<LocalCategoryData>>()
    val categories: LiveData<List<LocalCategoryData>> = _categories
    private val _subCategories = MutableLiveData<List<LocalSubcategoryData>>()
    val subCategories: LiveData<List<LocalSubcategoryData>> = _subCategories
    // key: Category ID, value: Subcategory
    // By the time categories is changed and observed, this variable should be initialized.
    lateinit var subcategoryReference: HashMap<Int, MutableList<LocalSubcategoryData>>
    private set

    private val _searchResult = MutableLiveData<List<SearchData>>()
    val searchResult: LiveData<List<SearchData>> = _searchResult

    private val currentOrdersInternal = hashMapOf<String, Int>()
    private val _currentOrders = MutableStateFlow(currentOrdersInternal)
    val currentOrders = _currentOrders.asStateFlow()

    private val _isProcessing = MutableLiveData(true)
    val isProcessing: LiveData<Boolean> = _isProcessing

    init {
        viewModelScope.launch {
            combine(
                categoryRepository.getGroceryCategories(),
                categoryRepository.getGrocerySubcategories()
            ) { category,subcategory -> Pair(category, subcategory) }
            .collect {
                // listOf() is necessary since system thinks emit() might not run.
                _categories.postValue(it.first ?: listOf())
                _subCategories.postValue(it.second ?: listOf())
                subcategoryReference = hashMapOf<Int, MutableList<LocalSubcategoryData>>().apply{
                    it.second.forEach { s -> getOrPut(s.catId) { mutableListOf() }.add(s) }
                }
            }
        }
    }

    fun finishedLoading() {
        _isProcessing.value = false
    }

    fun searchGroceryProduct(query: String) {
        _isProcessing.value = true
        viewModelScope.launch(ioDispatcher) {
            categoryRepository.searchGroceryProduct(query)
                .collect{ _searchResult.postValue(it) }
        }.invokeOnCompletion { _isProcessing.postValue(false) }
    }

    fun searchGroceryProductByCategory(catId: Int) {
        _isProcessing.value = true
        viewModelScope.launch(ioDispatcher) {
            categoryRepository.getGroceryProductByCategory(catId)
                .collect{ _searchResult.postValue(it) }
        }.invokeOnCompletion { _isProcessing.postValue(false) }
    }

    fun searchGroceryProductBySubcategory(subId: Int) {
        _isProcessing.value = true
        viewModelScope.launch(ioDispatcher) {
            categoryRepository.getGroceryProductBySubcategory(subId)
                .collect{ _searchResult.postValue(it) }
        }.invokeOnCompletion { _isProcessing.postValue(false) }
    }

    fun getSuggestedProduct(query: String) {
        // rmb to take from local before going api.
    }

    fun setProductOrder(productId: String, newAmount: Int) {
        if (newAmount <= 0) currentOrdersInternal.remove(productId)
        else currentOrdersInternal[productId] = newAmount
        _currentOrders.value = currentOrdersInternal
    }
}