package com.premiumgrocery.freshandfast.remote.model.orderresponse

import com.premiumgrocery.freshandfast.remote.model.ErrorResponse

sealed class OrderResponseSealed(
    val isSuccess: Boolean,
    val successResponse: OrderResponse? = null,
    val errorResponse: ErrorResponse? = null
) {
    data class Success(val res: OrderResponse):
        OrderResponseSealed(isSuccess = true, successResponse = res)
    data class Error(val err: ErrorResponse):
        OrderResponseSealed(isSuccess = false, errorResponse = err)
}
