package com.coffee.shop.di

import android.content.Context
import com.cofee.shop.BuildConfig
import com.coffee.shop.data.remote.NetworkApiService
import com.coffee.shop.data.remote.interceptors.HeaderInterceptor
import com.coffee.shop.data.remote.interceptors.NoConnectionInterceptor
import com.coffee.shop.utils.AppConstant.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


/**
 * The Dagger Module to provide the instances of [OkHttpClient], [Retrofit], and [NetworkApiService] classes.
 */
@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            httpClient.addInterceptor(logging)
        }
        return httpClient
            .addInterceptor(NoConnectionInterceptor(context)).addInterceptor(HeaderInterceptor())
            .connectTimeout(15, TimeUnit.SECONDS).readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS).build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Singleton
    @Provides
    fun providesApiService(retrofit: Retrofit): NetworkApiService {
        return retrofit.create(NetworkApiService::class.java)
    }
}