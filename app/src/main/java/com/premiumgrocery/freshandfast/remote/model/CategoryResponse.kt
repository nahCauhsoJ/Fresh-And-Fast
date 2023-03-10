package com.premiumgrocery.freshandfast.remote.model


import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    @SerializedName("count")
    val count: Int,
    @SerializedName("data")
    val `data`: List<CategoryData>,
    @SerializedName("error")
    val error: Boolean
)