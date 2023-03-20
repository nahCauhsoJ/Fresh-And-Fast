package com.premiumgrocery.freshandfast.remote.model.orderrequest

import com.google.gson.annotations.SerializedName

data class OrderRequestItem(
    @SerializedName("price")
    val price: Float,
    @SerializedName("quantity")
    val quantity: Int,
    @SerializedName("productName")
    val productName: String
)