package com.premiumgrocery.freshandfast.remote.model.orderrequest

import com.google.gson.annotations.SerializedName
import com.premiumgrocery.freshandfast.remote.model.ShippingAddress

data class OrderRequest(
    @SerializedName("userId")
    val userId: String,
    @SerializedName("orderSummary")
    val orderRequestSummary: OrderRequestSummary,
    @SerializedName("products")
    val products: List<OrderRequestItem>,
    @SerializedName("shippingAddress")
    val shippingAddress: ShippingAddress,
    @SerializedName("user")
    val user: OrderRequestUser,
)
