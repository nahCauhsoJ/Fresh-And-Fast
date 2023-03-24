package com.premiumgrocery.freshandfast.remote

import com.google.gson.Gson
import com.premiumgrocery.freshandfast.remote.model.ErrorResponse
import com.premiumgrocery.freshandfast.remote.model.ShippingAddress
import com.premiumgrocery.freshandfast.remote.model.orderrequest.*
import com.premiumgrocery.freshandfast.remote.model.orderresponse.OrderResponseSealed
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import kotlin.math.floor
import javax.inject.Inject
import kotlin.random.Random

class OrderRepository @Inject constructor(
    private val apiGrocery: ApiGrocery,
    private val gson: Gson
): IOrderRepository {
    private var randomDeliveryCharge = 0
    private var randomDiscount = 0
    init { randomizeExtras() }

    override suspend fun placeOrder(
        userId: String,
        userEmail: String,
        orders: List<OrderRequestItem>,
        shippingAddress: ShippingAddress,
        deliveryCharges: Int,
        discountAbsolute: Int
    ) = apiGrocery.placeOrder(OrderRequest(
        userId = userId,
        // Let's hardcode the extra charges for now.
        orderRequestSummary = orders.summarize(deliveryCharges, discountAbsolute),
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

    override suspend fun getDeliveryCharge() = flow { emit(randomDeliveryCharge) }
    override suspend fun getDiscount() = flow { emit(randomDiscount) }

    override fun randomizeExtras() {
        randomDeliveryCharge = Random.nextInt(0,10)
        randomDiscount = Random.nextInt(0,30)
    }
}

interface IOrderRepository {
    suspend fun placeOrder(
        userId: String,
        userEmail: String,
        orders: List<OrderRequestItem>,
        shippingAddress: ShippingAddress,
        deliveryCharges: Int,
        discountAbsolute: Int
    ): Response<OrderRequestResponse>
    suspend fun getOrders(userId: String): Flow<OrderResponseSealed>
    suspend fun getDeliveryCharge(): Flow<Int>
    suspend fun getDiscount(): Flow<Int>
    fun randomizeExtras()
}

fun List<OrderRequestItem>.summarize(
    deliveryCharges: Int,
    discountAbsolute: Int
) = sumOf { it.price.toDouble() * it.quantity }.toFloat().run {
    OrderRequestSummary(
        deliveryCharges = deliveryCharges,
        discount = discountAbsolute,
        totalAmount = this,
        ourPrice = floor(this + deliveryCharges - discountAbsolute).toInt()
    )
}

