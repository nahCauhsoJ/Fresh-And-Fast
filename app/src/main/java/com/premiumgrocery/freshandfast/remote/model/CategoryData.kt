package com.premiumgrocery.freshandfast.remote.model


import com.google.gson.annotations.SerializedName

data class CategoryData(
    @SerializedName("catDescription")
    val catDescription: String,
    @SerializedName("catId")
    val catId: Int,
    @SerializedName("catImage")
    val catImage: String,
    @SerializedName("catName")
    val catName: String,
    @SerializedName("_id")
    val id: String,
    @SerializedName("position")
    val position: Int,
    @SerializedName("slug")
    val slug: String,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("__v")
    val v: Int
)