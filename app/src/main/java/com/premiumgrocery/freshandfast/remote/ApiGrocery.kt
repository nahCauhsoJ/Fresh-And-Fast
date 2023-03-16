package com.premiumgrocery.freshandfast.remote

import com.premiumgrocery.freshandfast.remote.model.CategoryResponse
import com.premiumgrocery.freshandfast.remote.model.SearchResponse
import com.premiumgrocery.freshandfast.remote.model.SubcategoryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiGrocery {
    @GET("category")
    suspend fun getGroceryCategories(): Response<CategoryResponse>

    @GET("subcategory")
    suspend fun getGrocerySubcategories(): Response<SubcategoryResponse>

    @GET("products/cat/{catId}")
    suspend fun getGroceryProductByCategory(
        @Path("catId") catId: Int
    ): Response<SearchResponse>

    @GET("products/sub/{subId}")
    suspend fun getGroceryProductBySubcategory(
        @Path("subId") subId: Int
    ): Response<SearchResponse>

    @GET("products/search/{name}")
    suspend fun searchGroceryProduct(
        @Path("name") query: String
    ): Response<SearchResponse>
}