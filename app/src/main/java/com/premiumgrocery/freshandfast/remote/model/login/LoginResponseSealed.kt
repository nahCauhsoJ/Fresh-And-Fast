package com.premiumgrocery.freshandfast.remote.model.login

import com.premiumgrocery.freshandfast.remote.model.ErrorResponse

sealed class LoginResponseSealed(
    val isSuccess: Boolean,
    val successResponse: LoginSuccessResponse? = null,
    val errorResponse: ErrorResponse? = null
) {
    data class Success(val res: LoginSuccessResponse?):
        LoginResponseSealed(isSuccess = true, successResponse = res)
    data class Error(val err: ErrorResponse):
        LoginResponseSealed(isSuccess = false, errorResponse = err)
}

