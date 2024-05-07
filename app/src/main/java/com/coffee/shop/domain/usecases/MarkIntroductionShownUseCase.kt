package com.coffee.shop.domain.usecases

import com.coffee.shop.domain.repo.IRepository
import kotlinx.coroutines.CoroutineDispatcher

class MarkIntroductionShownUseCase(
    private val repository: IRepository, private val dispatcher: CoroutineDispatcher
) : FlowUseCase<Unit, Unit>(dispatcher) {
    public override fun execute(parameters: Unit) = repository.markIntroductionShown()
}