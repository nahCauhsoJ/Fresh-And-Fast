package com.premiumgrocery.freshandfast.remote.model


import com.google.gson.annotations.SerializedName
import com.premiumgrocery.freshandfast.local.model.LocalSubcategoryData

data class SubcategoryData(
    @SerializedName("catId")
    val catId: Int,
    @SerializedName("_id")
    val id: String,
    @SerializedName("position")
    val position: Int,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("subDescription")
    val subDescription: String,
    @SerializedName("subId")
    val subId: Int,
    @SerializedName("subImage")
    val subImage: String,
    @SerializedName("subName")
    val subName: String,
    @SerializedName("__v")
    val v: Int
) {
    fun toLocal() = LocalSubcategoryData(
        catId = catId,
        id = id,
        position = position,
        status = status,
        subDescription = subDescription,
        subId = subId,
        subImage = subImage,
        subName = subName,
        v = v
    )
}