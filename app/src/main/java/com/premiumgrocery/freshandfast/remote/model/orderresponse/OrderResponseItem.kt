package com.premiumgrocery.freshandfast.remote.model.orderresponse


import com.google.gson.annotations.SerializedName

data class OrderResponseItem(
    @SerializedName("_id")
    val id: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("productName")
    val productName: String,
    @SerializedName("quantity")
    val quantity: Int
)