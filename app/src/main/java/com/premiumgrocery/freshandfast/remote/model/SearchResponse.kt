package com.premiumgrocery.freshandfast.remote.model


import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("count")
    val count: Int,
    @SerializedName("data")
    val result: List<SearchData>,
    @SerializedName("error")
    val error: Boolean
)