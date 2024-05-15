package com.coffee.shop.di

import android.content.Context
import com.coffee.shop.data.local.CoffeeDao
import com.coffee.shop.data.local.UserDao
import com.coffee.shop.data.remote.NetworkApiService
import com.coffee.shop.domain.repo.IRepository
import com.coffee.shop.preferences.DatastorePreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.coffee.shop.data.repository.RepositoryImpl
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun provideRepository(
        @ApplicationContext context: Context,
        apiService: NetworkApiService,
        userDao: UserDao,
        coffeeDao: CoffeeDao,
        preferences: DatastorePreferences
    ): IRepository {
        return RepositoryImpl(
            context,
            apiService,
            userDao,
            coffeeDao,
            preferences,
            Dispatchers.IO
        )
    }

}