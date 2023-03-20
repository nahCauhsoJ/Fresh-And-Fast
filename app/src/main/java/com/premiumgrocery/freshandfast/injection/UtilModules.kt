package com.premiumgrocery.freshandfast.injection

import com.premiumgrocery.freshandfast.local.IUserOrderRepository
import com.premiumgrocery.freshandfast.local.UserOrderRepository
import com.premiumgrocery.freshandfast.remote.CategoryRepository
import com.premiumgrocery.freshandfast.remote.ICategoryRepository
import com.premiumgrocery.freshandfast.remote.ILoginRepository
import com.premiumgrocery.freshandfast.remote.LoginRepository
import com.premiumgrocery.freshandfast.utils.ILoginPrefAdapter
import com.premiumgrocery.freshandfast.utils.LoginPrefAdapter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UtilModules {
    @Binds
    abstract fun bindLoginPrefAdapter(impl: LoginPrefAdapter): ILoginPrefAdapter
}