package com.premiumgrocery.freshandfast.remote.model.login


import com.google.gson.annotations.SerializedName
import com.premiumgrocery.freshandfast.remote.model.UserData

data class RegisterSuccessResponse(
    @SerializedName("data")
    val result: UserData,
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("message")
    val message: String
)