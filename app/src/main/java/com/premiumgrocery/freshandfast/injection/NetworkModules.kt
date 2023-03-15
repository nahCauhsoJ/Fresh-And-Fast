package com.premiumgrocery.freshandfast.injection

import com.premiumgrocery.freshandfast.Const
import com.premiumgrocery.freshandfast.remote.ApiGrocery
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModules {
    @Singleton
    @Provides
    fun apiGrocery() = Retrofit.Builder()
        .baseUrl(Const.apiBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create<ApiGrocery>()

    @Singleton
    @Provides
    fun ioDispatcher() = Dispatchers.IO
}