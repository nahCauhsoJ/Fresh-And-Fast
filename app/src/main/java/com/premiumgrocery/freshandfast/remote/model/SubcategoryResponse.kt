package com.premiumgrocery.freshandfast.remote.model


import com.google.gson.annotations.SerializedName

data class SubcategoryResponse(
    @SerializedName("count")
    val count: Int,
    @SerializedName("data")
    val result: List<SubcategoryData>,
    @SerializedName("error")
    val error: Boolean
)