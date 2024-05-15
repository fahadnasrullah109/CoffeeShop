package com.coffee.shop.domain.usecases

import com.coffee.shop.data.DataResource
import com.coffee.shop.domain.models.DomainCoffee
import com.coffee.shop.domain.models.DomainNotification
import com.coffee.shop.domain.repo.IRepository
import kotlinx.coroutines.CoroutineDispatcher

class LoadFavouritesUseCase(
    private val repository: IRepository, private val dispatcher: CoroutineDispatcher
) : FlowUseCase<Unit, DataResource<List<DomainCoffee>>>(dispatcher) {
    public override fun execute(parameters: Unit) = repository.loadFavourites()
}