package com.premiumgrocery.freshandfast.remote.model.orderrequest

import com.google.gson.annotations.SerializedName

data class OrderRequestResponse(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("message")
    val message: String
)
