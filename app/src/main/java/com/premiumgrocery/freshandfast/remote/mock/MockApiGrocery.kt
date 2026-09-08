package com.premiumgrocery.freshandfast.remote.mock

import com.google.gson.Gson
import com.premiumgrocery.freshandfast.remote.ApiGrocery
import com.premiumgrocery.freshandfast.remote.model.*
import com.premiumgrocery.freshandfast.remote.model.login.LoginRequestBody
import com.premiumgrocery.freshandfast.remote.model.login.LoginSuccessResponse
import com.premiumgrocery.freshandfast.remote.model.login.RegisterRequestBody
import com.premiumgrocery.freshandfast.remote.model.orderrequest.OrderRequest
import com.premiumgrocery.freshandfast.remote.model.orderrequest.OrderRequestResponse
import com.premiumgrocery.freshandfast.remote.model.orderresponse.OrderResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import javax.inject.Inject

class MockApiGrocery @Inject constructor(private val gson: Gson) : ApiGrocery {

    private val authenticator = MockAuthenticator()

    override suspend fun postRegisterUser(registerBody: RegisterRequestBody): Response<Any> {
        return Response.success(Any())
    }

    override suspend fun postLoginUser(loginBody: LoginRequestBody): Response<Any> {
        val user = authenticator.authenticate(loginBody)
        return if (user != null) {
            Response.success(LoginSuccessResponse("mock_token", user))
        } else {
            val errorJson = gson.toJson(ErrorResponse(true, "Invalid credentials"))
            Response.error(401, errorJson.toResponseBody("application/json".toMediaTypeOrNull()))
        }
    }

    override suspend fun getGroceryCategories(): Response<CategoryResponse> {
        val data = gson.fromJson(MockResponses.CATEGORIES_JSON, CategoryResponse::class.java)
        return Response.success(data)
    }

    override suspend fun getGrocerySubcategories(): Response<SubcategoryResponse> {
        val data = gson.fromJson(MockResponses.SUBCATEGORIES_JSON, SubcategoryResponse::class.java)
        return Response.success(data)
    }

    override suspend fun getGroceryProductByCategory(catId: Int): Response<SearchResponse> {
        val data = gson.fromJson(MockResponses.PRODUCTS_JSON, SearchResponse::class.java)
        return Response.success(data)
    }

    override suspend fun getGroceryProductBySubcategory(subId: Int): Response<SearchResponse> {
        val data = gson.fromJson(MockResponses.PRODUCTS_JSON, SearchResponse::class.java)
        return Response.success(data)
    }

    override suspend fun searchGroceryProduct(query: String): Response<SearchResponse> {
        val data = gson.fromJson(MockResponses.PRODUCTS_JSON, SearchResponse::class.java)
        return Response.success(data)
    }

    override suspend fun getGroceryProductById(id: String): Response<ProductResponse> {
        val data = gson.fromJson(MockResponses.PRODUCT_DETAILS_JSON, ProductResponse::class.java)
        return Response.success(data)
    }

    override suspend fun placeOrder(orderRequest: OrderRequest): Response<OrderRequestResponse> {
        val data = gson.fromJson(MockResponses.ORDER_REQUEST_RESPONSE_JSON, OrderRequestResponse::class.java)
        return Response.success(data)
    }

    override suspend fun getOrders(userId: String): Response<OrderResponse> {
        val data = gson.fromJson(MockResponses.ORDERS_JSON, OrderResponse::class.java)
        return Response.success(data)
    }
}
