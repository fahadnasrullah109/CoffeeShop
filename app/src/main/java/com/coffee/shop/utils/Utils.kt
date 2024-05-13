package com.coffee.shop.utils

import com.coffee.shop.data.models.response.LoginResponse
import java.util.UUID

fun getRandomUUID() = UUID.randomUUID().toString()

fun getDummyLoginResponse() = LoginResponse(
    token = "token",
    ID = "ID",
    firstName = "John",
    lastName = "Doe",
    email = "john.doe@example.com",
    profilePicture = "https://fahadnasrullah109.github.io/cofeeshop/images/user_image.png",
    location = "Lahore, Pakistan"
)