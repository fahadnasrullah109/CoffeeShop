package com.coffee.shop.data.mappers
import com.coffee.shop.data.models.response.CoffeeCategory
import com.coffee.shop.domain.models.DomainCoffeeCategory

class CoffeeCategoryMapper {
    private val coffeeMapper by lazy { CoffeeMapper() }
    fun mapToDomain(category: CoffeeCategory): DomainCoffeeCategory {
        return DomainCoffeeCategory(
            category = category.category,
           items = category.items.map {
               coffeeMapper.mapToDomain(it)
           }
        )
    }
}