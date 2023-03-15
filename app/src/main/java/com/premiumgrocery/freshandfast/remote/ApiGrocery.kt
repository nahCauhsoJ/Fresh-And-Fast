package com.premiumgrocery.freshandfast.remote

import com.premiumgrocery.freshandfast.remote.model.CategoryResponse
import com.premiumgrocery.freshandfast.remote.model.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiGrocery {
    @GET("category")
    suspend fun getGroceryCategories(): Response<CategoryResponse>

    @GET("products/search/{name}")
    suspend fun searchGroceryProduct(
        @Path("name") query: String
    ): Response<SearchResponse>
}