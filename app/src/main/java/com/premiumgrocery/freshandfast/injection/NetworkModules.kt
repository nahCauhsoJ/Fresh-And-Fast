package com.premiumgrocery.freshandfast.injection

import com.google.gson.Gson
import com.premiumgrocery.freshandfast.remote.ApiGrocery
import com.premiumgrocery.freshandfast.remote.mock.MockApiGrocery
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModules {
    @Singleton
    @Provides
    fun apiGrocery(gson: Gson): ApiGrocery = MockApiGrocery(gson)

    @Singleton
    @Provides
    fun ioDispatcher() = Dispatchers.IO

    @Singleton
    @Provides
    fun gson() = Gson()
}
