package com.coffee.shop.data.remote

import com.coffee.shop.data.models.response.HomeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface NetworkApiService {
    @GET
    suspend fun getHomeData(@Url url: String): Response<HomeResponse>
}