package com.coffee.shop.domain.usecases

import com.coffee.shop.data.DataResource
import com.coffee.shop.domain.models.DomainHomeData
import com.coffee.shop.domain.repo.IRepository
import kotlinx.coroutines.CoroutineDispatcher

class HomeDataLoadUseCase(
    private val repository: IRepository, private val dispatcher: CoroutineDispatcher
) : FlowUseCase<Unit, DataResource<DomainHomeData>>(dispatcher) {
    public override fun execute(parameters: Unit) = repository.loadHomeScreenData()
}