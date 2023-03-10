package com.premiumgrocery.freshandfast.injection

import com.premiumgrocery.freshandfast.remote.CategoryRepository
import com.premiumgrocery.freshandfast.remote.ICategoryRepository
import com.premiumgrocery.freshandfast.remote.ILoginRepository
import com.premiumgrocery.freshandfast.remote.LoginRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModules {
    @Binds abstract fun bindCategoryRepository(impl: CategoryRepository): ICategoryRepository
    @Binds abstract fun bindLoginRepository(impl: LoginRepository): ILoginRepository
}