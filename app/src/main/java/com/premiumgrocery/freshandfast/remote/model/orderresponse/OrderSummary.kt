package com.premiumgrocery.freshandfast.remote.model.orderresponse


import com.google.gson.annotations.SerializedName

data class OrderSummary(
    @SerializedName("deliveryCharges")
    val deliveryCharges: Int,
    @SerializedName("discount")
    val discount: Int,
    @SerializedName("_id")
    val id: String,
    @SerializedName("ourPrice")
    val ourPrice: Int,
    @SerializedName("totalAmount")
    val totalAmount: Int
)