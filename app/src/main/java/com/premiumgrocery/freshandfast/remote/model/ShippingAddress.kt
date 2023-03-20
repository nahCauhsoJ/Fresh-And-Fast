package com.premiumgrocery.freshandfast.remote.model

import com.google.gson.annotations.SerializedName

data class ShippingAddress(
    @SerializedName("city")
    val city: String,
    @SerializedName("houseNo")
    val houseNo: String,
    @SerializedName("pincode")
    val pincode: Int,
    @SerializedName("streetName")
    val streetName: String,
    @SerializedName("type")
    val type: String,
)
