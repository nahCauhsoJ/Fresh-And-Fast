package com.premiumgrocery.freshandfast.remote.model


import com.google.gson.annotations.SerializedName

data class UserData(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("_id")
    val id: String,
    @SerializedName("mobile")
    val mobile: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("__v")
    val v: Int
)