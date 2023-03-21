package com.premiumgrocery.freshandfast.remote.model


import com.google.gson.annotations.SerializedName

data class LoginSuccessResponse(
    @SerializedName("token")
    val token: String,
    @SerializedName("user")
    val user: UserData
)