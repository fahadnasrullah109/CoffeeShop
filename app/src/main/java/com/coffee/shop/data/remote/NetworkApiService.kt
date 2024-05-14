package com.coffee.shop.data.remote

import com.coffee.shop.data.models.response.HomeResponse
import com.coffee.shop.data.models.response.Order
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface NetworkApiService {
    @GET
    suspend fun getHomeData(@Url url: String): Response<HomeResponse>

    @GET
    suspend fun getOrders(@Url url: String): Response<List<Order>>
}