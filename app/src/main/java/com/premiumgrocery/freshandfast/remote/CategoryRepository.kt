package com.premiumgrocery.freshandfast.remote

import com.premiumgrocery.freshandfast.local.ShopDao
import com.premiumgrocery.freshandfast.local.model.LocalCategoryData
import com.premiumgrocery.freshandfast.local.model.LocalSubcategoryData
import com.premiumgrocery.freshandfast.remote.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

class CategoryRepository @Inject constructor(
    private val apiGrocery: ApiGrocery,
    private val shopDao: ShopDao
): ICategoryRepository {
    private val cachedProductData = hashMapOf<String, ProductData>()

    override fun getGroceryCategories() = flow {
        val localData = shopDao.getCategories()
        if (localData.isNotEmpty()) { emit(localData); return@flow }
        apiGrocery.getGroceryCategories().body()?.result?.map { it.toLocal() }?.apply {
            shopDao.addCategories(this)
            emit(this)
            return@flow
        }
        emit(listOf())
    }

    override fun getGrocerySubcategories() = flow {
        val localData = shopDao.getSubcategories()
        if (localData.isNotEmpty()) { emit(localData); return@flow }
        apiGrocery.getGrocerySubcategories().body()?.result?.map { it.toLocal() }?.apply {
            shopDao.addSubcategories(this)
            emit(this)
            return@flow
        }
        emit(listOf())
    }

    override fun getGroceryProductByCategory(catId: Int) = flow {
        emit(apiGrocery.getGroceryProductByCategory(catId).body()?.result ?: listOf())
    }

    override fun getGroceryProductBySubcategory(subId: Int) = flow {
        emit(apiGrocery.getGroceryProductBySubcategory(subId).body()?.result ?: listOf())
    }

    override fun searchGroceryProduct(query: String) = flow {
        emit(apiGrocery.searchGroceryProduct(query).body()?.result ?: listOf())
    }

    override fun getGroceryProductByIds(
        ids: List<String>
    ) = flow { emit( HashMap(
        ids.associate { id ->
            if (cachedProductData.containsKey(id)) id to cachedProductData[id]
            else {
                id to withContext(coroutineContext) {
                    apiGrocery.getGroceryProductById(id).body()?.result.also {
                        // In real life cases, data like this should be received from a realtime database.
                        //      (Or at least the stock left for the product). But for this API,
                        //      I think it's ok to cache everything to save a lot of calls.
                        it?.let { d -> cachedProductData[id] = d }
                    }
                }
            }
        }
    ) ) }
}

interface ICategoryRepository {
    fun getGroceryCategories(): Flow<List<LocalCategoryData>>
    fun getGrocerySubcategories(): Flow<List<LocalSubcategoryData>>
    fun getGroceryProductByCategory(catId: Int): Flow<List<ProductData>>
    fun getGroceryProductBySubcategory(subId: Int): Flow<List<ProductData>>
    fun searchGroceryProduct(query: String): Flow<List<ProductData>>
    fun getGroceryProductByIds(ids: List<String>): Flow<HashMap<String, ProductData?>>
}