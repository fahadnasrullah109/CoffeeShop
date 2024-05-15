package com.coffee.shop.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DomainCoffee(
    val id: String,
    val title: String,
    val description: String,
    val detail: String,
    val priceSmall: Double,
    val priceMedium: Double,
    val priceLarge: Double,
    val rating: Double,
    val image: String
) : Parcelable