package com.coffee.shop.domain.usecases

import com.coffee.shop.data.DataResource
import com.coffee.shop.domain.repo.IRepository
import kotlinx.coroutines.CoroutineDispatcher

class ShowIntroductionDecisionUseCase(
    private val repository: IRepository, private val dispatcher: CoroutineDispatcher
) : FlowUseCase<Unit, DataResource<Boolean>>(dispatcher) {
    public override fun execute(parameters: Unit) = repository.shouldShowIntroduction()
}