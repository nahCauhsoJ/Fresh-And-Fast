package com.premiumgrocery.freshandfast.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.premiumgrocery.freshandfast.local.model.LocalCategoryData
import com.premiumgrocery.freshandfast.local.model.LocalSubcategoryData

@Database(entities = [
    LocalCategoryData::class,
    LocalSubcategoryData::class], version = 1, exportSchema = false)
abstract class RoomDB: RoomDatabase() {
    abstract fun shopDao(): ShopDao
}