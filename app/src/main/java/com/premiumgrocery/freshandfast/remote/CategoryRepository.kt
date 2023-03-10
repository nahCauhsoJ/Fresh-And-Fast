package com.premiumgrocery.freshandfast.remote

import com.premiumgrocery.freshandfast.remote.model.CategoryResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val apiGrocery: ApiGrocery
): ICategoryRepository {
    override fun getGroceryCategories() = flow {
        emit(apiGrocery.getGroceryCategories())
    }
}

interface ICategoryRepository {
    fun getGroceryCategories(): Flow<CategoryResponse>
}