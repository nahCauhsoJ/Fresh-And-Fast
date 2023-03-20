package com.premiumgrocery.freshandfast.local

import com.premiumgrocery.freshandfast.local.model.CurrentUserOrder
import com.premiumgrocery.freshandfast.remote.model.ProductData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserOrderRepository @Inject constructor(
    private val orderDao: OrderDao,
    private val ioDispatcher: CoroutineDispatcher
): IUserOrderRepository {
    override fun updateOrders(orders: HashMap<String, Int>) = orderDao.saveCurrentOrders(
        orders.map { CurrentUserOrder(it.key, it.value) }
    )
    override fun getOrders() = orderDao.getCurrentOrders().map { data ->
        HashMap(data.associate { it.id to it.amount })
    }

    override fun deleteOrder(id: String) = orderDao.deleteCurrentOrder(id)
    override suspend fun updateTotalOrderCost(
        currentOrder: HashMap<String, Int>,
        products: HashMap<String, ProductData?>
    ): Double = withContext(ioDispatcher) {
        // For business reasons, if the API somehow cannot return ALL product data
        //      accurately, instead of marking it as costing $0, it should throw an
        //      error instead. Hence the !! mark.
        var totalCost = 0.0
        try {
            currentOrder.forEach{
                totalCost += products[it.key]!!.price * it.value
            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
        return@withContext totalCost
    }
}

interface IUserOrderRepository {
    fun updateOrders(orders: HashMap<String, Int>)
    fun getOrders(): Flow<HashMap<String, Int>>
    fun deleteOrder(id: String)
    suspend fun updateTotalOrderCost(
        currentOrder: HashMap<String, Int>,
        products: HashMap<String, ProductData?>
    ): Double
}