package com.premiumgrocery.freshandfast.remote

import com.premiumgrocery.freshandfast.remote.model.ShippingAddress
import com.premiumgrocery.freshandfast.remote.model.orderrequest.*
import com.premiumgrocery.freshandfast.remote.model.orderresponse.OrderResponse
import retrofit2.Response
import kotlin.math.floor
import javax.inject.Inject

class OrderRepository @Inject constructor(
    private val apiGrocery: ApiGrocery
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

    override suspend fun getOrders(userId: String) {

    }
}

interface IOrderRepository {
    suspend fun placeOrder(
        userId: String,
        userEmail: String,
        orders: List<OrderRequestItem>,
        shippingAddress: ShippingAddress
    ): Response<OrderResponse>
    suspend fun getOrders(userId: String)
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

