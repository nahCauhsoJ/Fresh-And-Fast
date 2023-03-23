package com.premiumgrocery.freshandfast.remote.model.login


import com.google.gson.annotations.SerializedName
import com.premiumgrocery.freshandfast.remote.model.UserData

data class LoginSuccessResponse(
    @SerializedName("token")
    val token: String,
    @SerializedName("user")
    val user: UserData
)