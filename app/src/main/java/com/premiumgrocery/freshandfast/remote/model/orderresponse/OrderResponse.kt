package com.premiumgrocery.freshandfast.remote.model.orderresponse


import com.google.gson.annotations.SerializedName

data class OrderResponse(
    @SerializedName("data")
    val result: OrderResponseData,
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("message")
    val message: String
)