package com.premiumgrocery.freshandfast.ui.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.premiumgrocery.freshandfast.Const
import com.premiumgrocery.freshandfast.local.UserOrderRepository
import com.premiumgrocery.freshandfast.remote.CategoryRepository
import com.premiumgrocery.freshandfast.remote.model.ProductData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val userOrderRepository: UserOrderRepository,
    private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    private val _processTasks = MutableStateFlow(listOf(Const.processLabelStart))
    val processTasks = _processTasks.asStateFlow()

    val currentOrdersStatic = hashMapOf<String, Int>()
    val currentOrders = getProductOrders().stateIn(viewModelScope, SharingStarted.Eagerly, hashMapOf())
    private val _orderDetails = MutableLiveData<List<ProductData>>()
    val orderDetails: LiveData<List<ProductData>> = _orderDetails
    private val _orderTotalCost = MutableStateFlow(0.0)
    val orderTotalCost = _orderTotalCost.asStateFlow()

    init {
        addProcessTask(Const.processLabelGetCurrentProducts)
        viewModelScope.launch(ioDispatcher) {
            currentOrders.collect {
                currentOrdersStatic.clear()
                currentOrdersStatic.putAll(it)
                endProcessTask(Const.processLabelGetCurrentProducts)
                if (it.isNotEmpty()) {
                    updateOrderDetails(it.keys.toList())
                    updateTotalOrderCost()
                } else {
                    _orderTotalCost.emit(0.0)
                    _orderDetails.postValue(listOf())
                }
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

    fun removeProductOrder(productId: String) {
        addProcessTask(Const.processLabelDeleteCurrentProducts)
        viewModelScope.launch(ioDispatcher) {
            userOrderRepository.deleteOrder(productId)
            endProcessTask(Const.processLabelDeleteCurrentProducts)
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

    private fun updateOrderDetails(idList: List<String>) {
        addProcessTask(Const.processLabelSearchProduct)
        viewModelScope.launch(ioDispatcher) {
            categoryRepository.getGroceryProductByIds(idList).collect{ data ->
                _orderDetails.postValue(
                    data.values.filterNotNull().filter{(currentOrdersStatic[it.id] ?: 0) > 0 }
                )
                endProcessTask(Const.processLabelSearchProduct)
            }
        }
    }

    fun calculateTotalCost(price: Double, quantity: Int) = price * quantity

}