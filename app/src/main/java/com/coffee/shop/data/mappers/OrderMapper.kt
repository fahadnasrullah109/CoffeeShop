package com.coffee.shop.data.mappers

import com.coffee.shop.data.models.response.Order
import com.coffee.shop.domain.models.DomainOrder

class OrderMapper {
    fun mapToDomain(order: Order): DomainOrder {
        return DomainOrder(
            title = order.title,
            description = order.description,
            price = order.price,
            quantity = order.quantity,
            date = order.date,
            rating = order.rating,
            image = order.image
        )
    }
}