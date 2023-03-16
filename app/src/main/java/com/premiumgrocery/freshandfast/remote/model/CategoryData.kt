package com.premiumgrocery.freshandfast.remote.model


import com.google.gson.annotations.SerializedName
import com.premiumgrocery.freshandfast.local.model.LocalCategoryData

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
) {
    fun toLocal() = LocalCategoryData(
        catDescription = catDescription,
        catId = catId,
        catImage = catImage,
        catName = catName,
        id = id,
        position = position,
        slug = slug,
        status = status,
        v = v
    )
}