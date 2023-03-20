package com.premiumgrocery.freshandfast.ui.order

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.premiumgrocery.freshandfast.Const
import com.premiumgrocery.freshandfast.OrderStatus
import com.premiumgrocery.freshandfast.local.UserOrderRepository
import com.premiumgrocery.freshandfast.remote.CategoryRepository
import com.premiumgrocery.freshandfast.remote.OrderRepository
import com.premiumgrocery.freshandfast.remote.model.ProductData
import com.premiumgrocery.freshandfast.remote.model.orderrequest.OrderRequestItem
import com.premiumgrocery.freshandfast.utils.LoginPrefAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@SuppressLint("StaticFieldLeak")
class OrderViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val userOrderRepository: UserOrderRepository,
    private val orderRepository: OrderRepository,
    private val loginPrefAdapter: LoginPrefAdapter,
    private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    private val _processTasks = MutableStateFlow(listOf(Const.processLabelStart))
    val processTasks = _processTasks.asStateFlow()

    // Just a place to store a state for the fragment.
    var ordering = false

    val currentOrdersStatic = hashMapOf<String, Int>()
    val currentOrders = getProductOrders().stateIn(viewModelScope, SharingStarted.Eagerly, hashMapOf())
    private val _orderDetails = MutableLiveData<List<ProductData>>()
    val orderDetails: LiveData<List<ProductData>> = _orderDetails
    private val _orderTotalCost = MutableStateFlow(0.0)
    val orderTotalCost = _orderTotalCost.asStateFlow()
    private val _placeOrderStatus = MutableLiveData<OrderStatus?>()
    val placeOrderStatus: LiveData<OrderStatus?> = _placeOrderStatus

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

    // This is the trigger for placing an order. Beware of when to use it.
    fun submitOrder() {
        addProcessTask(Const.processLabelPlaceOrder)
        viewModelScope.launch(ioDispatcher) {
            val userId = loginPrefAdapter.getUserId()
            val userEmail = loginPrefAdapter.getUserEmail()
            val orders = try {
                if (currentOrders.value.isEmpty()) throw java.lang.Exception("currentOrders is empty")
                currentOrders.value.map {
                    val product = orderDetails.value!!.first { x->x.id == it.key }
                    OrderRequestItem(
                        productName = product.productName,
                        price = product.price.toFloat(),
                        quantity = it.value
                    )
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                // This mean something is wrong with orderDetails not in-sync
                //      or orderDetails' LiveData is null. In either case it's
                //      better to return null and stop the transaction.

                null
            }

            if (userId != null && userEmail != null && orders != null) {
                orderRepository.placeOrder(
                    userId,
                    userEmail,
                    orders,
                    Const.placeholderShippingAddress
                ).also {
                    userOrderRepository.clearOrder()
                    _placeOrderStatus.postValue(OrderStatus.SUCCESS)
                    // Find a way to make the view post a null after receiving.
                    _placeOrderStatus.postValue(null)
                    endProcessTask(Const.processLabelPlaceOrder)
                }
            } else {
                endProcessTask(Const.processLabelPlaceOrder)
                _placeOrderStatus.postValue(OrderStatus.FAIL)
            }
        }
    }

    fun calculateTotalCost(price: Double, quantity: Int) = price * quantity

}