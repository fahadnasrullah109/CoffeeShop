package com.coffee.shop.domain.usecases

import com.coffee.shop.data.DataResource
import com.coffee.shop.domain.models.DomainNotification
import com.coffee.shop.domain.repo.IRepository
import kotlinx.coroutines.CoroutineDispatcher

class LoadNotificationsUseCase(
    private val repository: IRepository, private val dispatcher: CoroutineDispatcher
) : FlowUseCase<Unit, DataResource<List<DomainNotification>>>(dispatcher) {
    public override fun execute(parameters: Unit) = repository.loadNotifications()
}