package com.coffee.shop.domain.usecases

import com.coffee.shop.data.DataResource
import com.coffee.shop.data.models.response.LoginResponse
import com.coffee.shop.domain.repo.IRepository
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Response

class RegisterUseCase(
    private val repository: IRepository, private val dispatcher: CoroutineDispatcher
) : FlowUseCase<Unit, DataResource<Response<LoginResponse>>>(dispatcher) {
    public override fun execute(parameters: Unit) = repository.register()
}