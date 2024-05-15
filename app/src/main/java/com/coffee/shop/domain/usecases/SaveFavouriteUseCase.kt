package com.coffee.shop.domain.usecases

import com.coffee.shop.data.DataResource
import com.coffee.shop.domain.models.DomainCoffee
import com.coffee.shop.domain.repo.IRepository
import kotlinx.coroutines.CoroutineDispatcher

class SaveFavouriteUseCase(
    private val repository: IRepository, private val dispatcher: CoroutineDispatcher
) : FlowUseCase<DomainCoffee, DataResource<Boolean>>(dispatcher) {
    public override fun execute(parameters: DomainCoffee) = repository.saveFavourite(parameters)
}