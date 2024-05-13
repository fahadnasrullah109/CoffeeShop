package com.coffee.shop.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DomainHomeData(
    val user: DomainUser,
    val promoUrl: String? = null,
    val coffeeCategories: List<DomainCoffeeCategory>
) : Parcelable