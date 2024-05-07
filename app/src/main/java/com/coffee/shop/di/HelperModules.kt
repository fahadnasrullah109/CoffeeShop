package com.coffee.shop.di

import android.content.Context
import com.coffee.shop.preferences.DatastorePreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HelperModules {

    @Singleton
    @Provides
    fun provideDataStorePreferences(@ApplicationContext context: Context) =
        DatastorePreferences(context)
}