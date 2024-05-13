package com.coffee.shop.data.mappers

import com.coffee.shop.data.models.response.HomeResponse
import com.coffee.shop.domain.models.DomainHomeData

class HomeDataMapper {
    private val userMapper by lazy { UserMapper() }
    fun mapToDomain(response: HomeResponse): DomainHomeData {
        return DomainHomeData(
            user = userMapper.mapToDomain(response.user),
            promoUrl = response.promoUrl,
            coffeeCategories = response.coffeeCategories
        )
    }
}