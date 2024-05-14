package com.coffee.shop.utils

import com.coffee.shop.data.models.response.LoginResponse
import com.coffee.shop.domain.models.DomainCoffee
import com.coffee.shop.domain.models.DomainOrder
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

fun getDummyDomainCoffee() = DomainCoffee(
    title = "dummyTitle",
    description = "dummyDescription",
    detail = "The Americano, a revered staple in the world of coffee, is a testament to simplicity and versatility. Originating during World War II in Europe, its creation was sparked by the resourcefulness of American soldiers seeking a taste of home amidst the espresso-dominated café culture. Crafted by diluting a shot of bold espresso with hot water, the Americano offers a unique flavor profile that marries the intensity of espresso with the smoothness of water. This blending of elements results in a beverage that boasts a robust yet mellow character, with layers of complexity that unfold with each sip. From the rich aroma of freshly brewed coffee to the clean, crisp finish that lingers on the palate, the Americano embodies the essence of balance and harmony. Its versatility extends beyond its preparation, as it serves as a canvas for customization, allowing aficionados to tailor its strength and flavor to their liking. Whether enjoyed as a morning ritual, a midday pick-me-up, or a leisurely indulgence, the Americano remains a timeless classic, cherished for its simplicity, adaptability, and ability to evoke a sense of comfort and familiarity in every sip.",
    priceSmall = 5.0,
    priceMedium = 6.0,
    priceLarge = 7.0,
    rating = 4.5,
    image = "image.png"
)

fun getDummyDomainOrder() = DomainOrder(
    title = "dummyTitle",
    description = "dummyDescription",
    price = 5.0,
    quantity = 6,
    rating = 4.5,
    date = "02/02/2024",
    image = "image.png"
)