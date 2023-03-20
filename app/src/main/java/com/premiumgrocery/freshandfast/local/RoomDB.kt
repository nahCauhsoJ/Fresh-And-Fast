package com.premiumgrocery.freshandfast.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.premiumgrocery.freshandfast.local.model.CurrentUserOrder
import com.premiumgrocery.freshandfast.local.model.LocalCategoryData
import com.premiumgrocery.freshandfast.local.model.LocalSubcategoryData

@Database(entities = [
    LocalCategoryData::class,
    LocalSubcategoryData::class,
    CurrentUserOrder::class
                     ], version = 1, exportSchema = false)
@TypeConverters(RoomTypeConverters::class)
abstract class RoomDB: RoomDatabase() {
    abstract fun shopDao(): ShopDao
    abstract fun orderDao(): OrderDao
}