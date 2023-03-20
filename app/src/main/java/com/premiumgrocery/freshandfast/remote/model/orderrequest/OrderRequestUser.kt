package com.premiumgrocery.freshandfast.remote.model.orderrequest

import com.google.gson.annotations.SerializedName

data class OrderRequestUser(
    @SerializedName("email")
    val email: String,
    @SerializedName("orderStatus")
    val orderStatus: String = "completed"
)