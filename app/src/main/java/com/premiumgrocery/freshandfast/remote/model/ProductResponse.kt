package com.premiumgrocery.freshandfast.remote.model


import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("data")
    val result: ProductData,
    @SerializedName("error")
    val error: Boolean
)