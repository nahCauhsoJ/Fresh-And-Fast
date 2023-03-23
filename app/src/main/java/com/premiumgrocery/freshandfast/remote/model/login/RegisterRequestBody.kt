package com.premiumgrocery.freshandfast.remote.model.login

data class RegisterRequestBody(
    val firstName: String,
    val email: String,
    val password: String,
    val mobile: String
)
