package com.coffee.shop.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DomainCoffee(
    val title: String,
    val description: String,
    val price: Double,
    val rating: Double,
    val image: String,
) : Parcelable