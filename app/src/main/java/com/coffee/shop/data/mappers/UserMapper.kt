package com.coffee.shop.data.mappers

import com.coffee.shop.data.models.db.User
import com.coffee.shop.domain.models.DomainUser

class UserMapper {
    fun mapToDomain(user: User): DomainUser {
        return DomainUser(
            userId = user.userId, name = user.name
        )
    }

    fun mapToDataModel(user: DomainUser): User {
        return User(
            userId = user.userId, name = user.name
        )
    }
}