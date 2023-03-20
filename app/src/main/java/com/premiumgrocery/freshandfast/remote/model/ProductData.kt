package com.premiumgrocery.freshandfast.remote.model


import com.google.gson.annotations.SerializedName

data class ProductData(
    @SerializedName("catId")
    val catId: Int,
    @SerializedName("created")
    val created: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("_id")
    val id: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("mrp")
    val mrp: Double,
    @SerializedName("position")
    val position: Int,
    @SerializedName("price")
    val price: Double,
    @SerializedName("productName")
    val productName: String,
    @SerializedName("quantity")
    val quantity: Int,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("subId")
    val subId: Int,
    @SerializedName("unit")
    val unit: String,
    @SerializedName("__v")
    val v: Int
)