package com.premiumgrocery.freshandfast.remote.model.orderresponse


import com.google.gson.annotations.SerializedName
import com.premiumgrocery.freshandfast.remote.model.ShippingAddress

data class OrderResponseData(
    @SerializedName("date")
    val date: String,
    @SerializedName("_id")
    val id: String,
    @SerializedName("orderSummary")
    val orderSummary: OrderSummary,
    @SerializedName("products")
    val products: List<OrderResponseItem>,
    @SerializedName("shippingAddress")
    val shippingAddress: ShippingAddress,
    @SerializedName("user")
    val user: OrderResponseUser,
    @SerializedName("userId")
    val userId: String,
    @SerializedName("__v")
    val v: Int
)