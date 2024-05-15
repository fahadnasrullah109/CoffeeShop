package com.coffee.shop.di

import android.content.Context
import com.coffee.shop.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.init(context)
    }

    @Singleton
    @Provides
    fun provideUserDao(roomDB: AppDatabase) = roomDB.userDao()

    @Singleton
    @Provides
    fun provideCoffeeDao(roomDB: AppDatabase) = roomDB.coffeeDao()

}