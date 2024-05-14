package com.coffee.shop.data.mappers

import com.coffee.shop.data.models.response.Coffee
import com.coffee.shop.domain.models.DomainCoffee

class CoffeeMapper {
    fun mapToDomain(coffee: Coffee): DomainCoffee {
        return DomainCoffee(
            title = coffee.title,
            description = coffee.description,
            detail = coffee.detail,
            priceSmall = coffee.priceSmall,
            priceMedium = coffee.priceMedium,
            priceLarge = coffee.priceLarge,
            rating = coffee.rating,
            image = coffee.image
        )
    }
}