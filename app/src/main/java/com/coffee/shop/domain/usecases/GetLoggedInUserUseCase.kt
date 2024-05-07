package com.coffee.shop.domain.usecases

import com.coffee.shop.data.DataResource
import com.coffee.shop.domain.repo.IRepository
import com.coffee.shop.domain.models.DomainUser
import kotlinx.coroutines.CoroutineDispatcher

class GetLoggedInUserUseCase(
    private val repository: IRepository, private val dispatcher: CoroutineDispatcher
) : FlowUseCase<Unit, DataResource<DomainUser?>>(dispatcher) {
    public override fun execute(parameters: Unit) = repository.getLoggedInUser()
}