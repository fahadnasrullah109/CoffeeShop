package com.coffee.shop.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DomainOrder(
    val title: String,
    val description: String,
    val price: Double,
    val quantity: Int,
    val rating: Double,
    val date: String,
    val image: String,
) : Parcelable