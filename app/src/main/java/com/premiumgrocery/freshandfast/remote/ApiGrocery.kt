package com.premiumgrocery.freshandfast.remote

import com.premiumgrocery.freshandfast.remote.model.CategoryResponse
import retrofit2.http.GET

interface ApiGrocery {
    @GET("category")
    suspend fun getGroceryCategories(): CategoryResponse
}