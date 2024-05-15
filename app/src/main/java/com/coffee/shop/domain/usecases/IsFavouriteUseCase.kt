package com.coffee.shop.domain.usecases

import com.coffee.shop.data.DataResource
import com.coffee.shop.domain.models.DomainCoffee
import com.coffee.shop.domain.repo.IRepository
import kotlinx.coroutines.CoroutineDispatcher

class IsFavouriteUseCase(
    private val repository: IRepository, private val dispatcher: CoroutineDispatcher
) : FlowUseCase<String, DataResource<Boolean>>(dispatcher) {
    public override fun execute(parameters: String) = repository.isFavourite(parameters)
}