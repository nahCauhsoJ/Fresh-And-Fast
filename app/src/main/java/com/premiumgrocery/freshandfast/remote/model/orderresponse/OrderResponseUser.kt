package com.premiumgrocery.freshandfast.remote.model.orderresponse


import com.google.gson.annotations.SerializedName

data class OrderResponseUser(
    @SerializedName("email")
    val email: String,
    @SerializedName("_id")
    val id: String
)