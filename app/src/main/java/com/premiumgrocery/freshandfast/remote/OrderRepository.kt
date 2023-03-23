package com.premiumgrocery.freshandfast.remote

import com.google.gson.Gson
import com.premiumgrocery.freshandfast.remote.model.ErrorResponse
import com.premiumgrocery.freshandfast.remote.model.ShippingAddress
import com.premiumgrocery.freshandfast.remote.model.login.LoginResponseSealed
import com.premiumgrocery.freshandfast.remote.model.orderrequest.*
import com.premiumgrocery.freshandfast.remote.model.orderresponse.OrderResponse
import com.premiumgrocery.freshandfast.remote.model.orderresponse.OrderResponseData
import com.premiumgrocery.freshandfast.remote.model.orderresponse.OrderResponseSealed
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import kotlin.math.floor
import javax.inject.Inject

class OrderRepository @Inject constructor(
    private val apiGrocery: ApiGrocery,
    private val gson: Gson
): IOrderRepository {
    override suspend fun placeOrder(
        userId: String,
        userEmail: String,
        orders: List<OrderRequestItem>,
        shippingAddress: ShippingAddress
    ) = apiGrocery.placeOrder(OrderRequest(
        userId = userId,
        // Let's hardcode the extra charges for now.
        orderRequestSummary = orders.summarize(deliveryFree = 5, discountAbsolute = 10),
        products = orders,
        shippingAddress = shippingAddress,
        user = OrderRequestUser(userEmail)
    ))

    override suspend fun getOrders(userId: String) = flow {
        apiGrocery.getOrders(userId).apply {
            if (isSuccessful) body()?.let { emit(OrderResponseSealed.Success(it)) }
            else errorBody()?.let {
                emit(OrderResponseSealed.Error(
                    gson.fromJson(it.string(), ErrorResponse::class.java)
                ))
            }
        }
    }
}

interface IOrderRepository {
    suspend fun placeOrder(
        userId: String,
        userEmail: String,
        orders: List<OrderRequestItem>,
        shippingAddress: ShippingAddress
    ): Response<OrderResponse>
    suspend fun getOrders(userId: String): Flow<OrderResponseSealed>
}

fun List<OrderRequestItem>.summarize(
    deliveryFree: Int,
    discountAbsolute: Int
) = sumOf { it.price.toDouble() * it.quantity }.toFloat().run {
    OrderRequestSummary(
        deliveryCharges = deliveryFree,
        discount = discountAbsolute,
        totalAmount = this,
        ourPrice = floor(this + deliveryFree - discountAbsolute).toInt()
    )
}

