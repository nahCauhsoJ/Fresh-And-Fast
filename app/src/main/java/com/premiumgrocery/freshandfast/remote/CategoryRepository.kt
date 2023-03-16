package com.premiumgrocery.freshandfast.remote

import com.premiumgrocery.freshandfast.local.ShopDao
import com.premiumgrocery.freshandfast.local.model.LocalCategoryData
import com.premiumgrocery.freshandfast.local.model.LocalSubcategoryData
import com.premiumgrocery.freshandfast.remote.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val apiGrocery: ApiGrocery,
    private val shopDao: ShopDao
): ICategoryRepository {
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
}

interface ICategoryRepository {
    fun getGroceryCategories(): Flow<List<LocalCategoryData>>
    fun getGrocerySubcategories(): Flow<List<LocalSubcategoryData>>
    fun getGroceryProductByCategory(catId: Int): Flow<List<SearchData>>
    fun getGroceryProductBySubcategory(subId: Int): Flow<List<SearchData>>
    fun searchGroceryProduct(query: String): Flow<List<SearchData>>
}