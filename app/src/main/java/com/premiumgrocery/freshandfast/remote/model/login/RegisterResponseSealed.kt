package com.premiumgrocery.freshandfast.remote.model.login

import com.premiumgrocery.freshandfast.remote.model.ErrorResponse

sealed class RegisterResponseSealed(
    val isSuccess: Boolean,
    val successResponse: RegisterSuccessResponse? = null,
    val errorResponse: ErrorResponse? = null
) {
    data class Success(val res: RegisterSuccessResponse):
        RegisterResponseSealed(isSuccess = true, successResponse = res)
    data class Error(val err: ErrorResponse):
        RegisterResponseSealed(isSuccess = false, errorResponse = err)
}