package com.premiumgrocery.freshandfast.remote

import com.premiumgrocery.freshandfast.remote.model.*
import com.premiumgrocery.freshandfast.remote.model.orderrequest.OrderRequest
import com.premiumgrocery.freshandfast.remote.model.orderresponse.OrderResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiGrocery {

    @POST("auth/register")
    suspend fun postRegisterUser(
        @Body registerBody: RegisterRequestBody
    ): Response<Any>

    @POST("auth/login")
    suspend fun postLoginUser(
        @Body loginBody: LoginRequestBody
    ): Response<Any>

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

    @GET("products/{id}")
    suspend fun getGroceryProductById(
        @Path("id") id: String
    ): Response<ProductResponse>

    // Note that a lot of responses here either emit the successful response
    //      like this one, or an Error Response. Normally they should all be
    //      Response<Any> and then cast the response. But ain't got no time
    //      to deal with this. Add them if you have time. Cheers 🍻
    @POST("orders")
    suspend fun placeOrder(
        @Body orderRequest: OrderRequest
    ): Response<OrderResponse>
}