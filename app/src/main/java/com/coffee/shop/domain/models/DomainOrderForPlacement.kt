package com.coffee.shop.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DomainOrderForPlacement(
    val orderPrice: Double,
    val shipping: Int,
    val totalPrice: Double
) : Parcelable