package com.coffee.shop.domain.repo

import com.coffee.shop.data.DataResource
import com.coffee.shop.data.models.response.LoginResponse
import com.coffee.shop.domain.models.DomainUser
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface IRepository {
    fun shouldShowIntroduction(): Flow<DataResource<Boolean>>
    fun getLoggedInUser(): Flow<DataResource<DomainUser?>>
    fun logout(): Flow<DataResource<Boolean>>
    fun login(): Flow<DataResource<Response<LoginResponse>>>
    fun register(): Flow<DataResource<Response<LoginResponse>>>
    fun forgotPassword(): Flow<DataResource<Response<LoginResponse>>>
    fun verifyOTP(): Flow<DataResource<Response<LoginResponse>>>
    fun markIntroductionShown(): Flow<Unit>
}