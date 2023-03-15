package com.premiumgrocery.freshandfast.remote

import com.premiumgrocery.freshandfast.remote.model.CategoryData
import com.premiumgrocery.freshandfast.remote.model.CategoryResponse
import com.premiumgrocery.freshandfast.remote.model.SearchData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val apiGrocery: ApiGrocery
): ICategoryRepository {
    override fun getGroceryCategories() = flow {
        emit(apiGrocery.getGroceryCategories().body()?.data ?: listOf())
    }

    override fun searchGroceryProduct(query: String) = flow {
        emit(apiGrocery.searchGroceryProduct(query).body()?.result ?: listOf())
    }
}

interface ICategoryRepository {
    fun getGroceryCategories(): Flow<List<CategoryData>>
    fun searchGroceryProduct(query: String): Flow<List<SearchData>>
}