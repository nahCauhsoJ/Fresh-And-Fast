package com.premiumgrocery.freshandfast.remote.model


import com.google.gson.annotations.SerializedName

data class RegisterSuccessResponse(
    @SerializedName("data")
    val result: UserData,
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("message")
    val message: String
)