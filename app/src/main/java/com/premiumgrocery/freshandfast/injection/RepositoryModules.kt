package com.premiumgrocery.freshandfast.injection

import com.premiumgrocery.freshandfast.local.IUserOrderRepository
import com.premiumgrocery.freshandfast.local.UserOrderRepository
import com.premiumgrocery.freshandfast.remote.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

// Most of these repositories are needed across multiple view models. Singleton is needed.
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModules {
    @Binds abstract fun bindLoginRepository(impl: LoginRepository): ILoginRepository
    @Binds abstract fun bindCategoryRepository(impl: CategoryRepository): ICategoryRepository
    @Binds abstract fun bindUserOrderRepository(impl: UserOrderRepository): IUserOrderRepository
    @Binds abstract fun bindOrderRepository(impl: OrderRepository): IOrderRepository
}