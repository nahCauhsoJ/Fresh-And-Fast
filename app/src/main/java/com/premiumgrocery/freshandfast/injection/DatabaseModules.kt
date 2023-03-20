package com.premiumgrocery.freshandfast.injection

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.room.Room
import com.premiumgrocery.freshandfast.Const
import com.premiumgrocery.freshandfast.local.RoomDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModules {
    @Provides
    fun provideShopDao(db: RoomDB) = db.shopDao()

    @Provides
    fun provideOrderDao(db: RoomDB) = db.orderDao()

    @Provides
    fun provideSharedPrefs(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(Const.sharedPrefName, MODE_PRIVATE)
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModules2 {
    @Provides
    @Singleton
    fun provideRoomDb(@ApplicationContext context: Context): RoomDB =
        Room.databaseBuilder( context, RoomDB::class.java, Const.dbName ).build()
}