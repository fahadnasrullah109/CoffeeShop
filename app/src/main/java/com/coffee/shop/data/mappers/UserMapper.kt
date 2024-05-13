package com.coffee.shop.data.mappers

import com.coffee.shop.data.models.db.User
import com.coffee.shop.domain.models.DomainUser
import com.coffee.shop.utils.getRandomUUID

class UserMapper {
    fun mapToDomain(user: User): DomainUser {
        return DomainUser(
            name = user.name,
            profilePicture = user.profilePicture,
            location = user.location
        )
    }

    fun mapToDataModel(user: DomainUser): User {
        return User(
            userId = getRandomUUID(),
            name = user.name,
            profilePicture = user.profilePicture,
            location = user.location
        )
    }
}