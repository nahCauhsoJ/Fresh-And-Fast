package com.premiumgrocery.freshandfast.remote.model

data class RegisterRequestBody(
    val firstName: String,
    val email: String,
    val password: String,
    val mobile: String
)
