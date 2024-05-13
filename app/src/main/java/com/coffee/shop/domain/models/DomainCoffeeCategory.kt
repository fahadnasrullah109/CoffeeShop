package com.coffee.shop.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DomainCoffeeCategory(
    val category: String,
    val items: List<DomainCoffee>
) : Parcelable