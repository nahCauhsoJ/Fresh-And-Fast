package com.premiumgrocery.freshandfast.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.premiumgrocery.freshandfast.Const
import com.premiumgrocery.freshandfast.local.model.LocalCategoryData
import com.premiumgrocery.freshandfast.local.model.LocalSubcategoryData

@Dao
interface ShopDao {
    @Query("select * from ${Const.dbTableCategory}")
    suspend fun getCategories(): List<LocalCategoryData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCategories(data: List<LocalCategoryData>)

    @Query("select * from ${Const.dbTableSubcategory}")
    suspend fun getSubcategories(): List<LocalSubcategoryData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSubcategories(data: List<LocalSubcategoryData>)
}