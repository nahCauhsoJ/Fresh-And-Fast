package com.premiumgrocery.freshandfast.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.premiumgrocery.freshandfast.Const
import com.premiumgrocery.freshandfast.remote.IOrderRepository
import com.premiumgrocery.freshandfast.remote.model.orderresponse.OrderResponseData
import com.premiumgrocery.freshandfast.remote.model.orderresponse.OrderResponseSealed
import com.premiumgrocery.freshandfast.utils.ILoginPrefAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val orderRepository: IOrderRepository,
    private val loginPrefAdapter: ILoginPrefAdapter,
    private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    private val _processTasks = MutableStateFlow(listOf(Const.processLabelStart))
    val processTasks = _processTasks.asStateFlow()

    private val _userOrders = MutableStateFlow<List<OrderResponseData>>(listOf())
    val userOrders = _userOrders.asStateFlow()

    fun getOrders() {
        addProcessTask(Const.processLabelGetOrders)
        viewModelScope.launch(ioDispatcher) {
            loginPrefAdapter.getUserId()?.let { userId ->
                orderRepository.getOrders(userId).collect{
                    if (it is OrderResponseSealed.Success) {
                        _userOrders.emit(it.successResponse!!.result)
                    }
                    endProcessTask(Const.processLabelGetOrders)
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
}