package com.premiumgrocery.freshandfast.remote.model.orderrequest

import com.google.gson.annotations.SerializedName

data class OrderRequestSummary (
    @SerializedName("deliveryCharges")
    val deliveryCharges: Int,
    @SerializedName("discount")
    val discount: Int,
    @SerializedName("ourPrice")
    val ourPrice: Int,
    @SerializedName("totalAmount")
    val totalAmount: Float
)