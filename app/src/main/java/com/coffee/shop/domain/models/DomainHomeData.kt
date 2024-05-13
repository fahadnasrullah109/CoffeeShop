package com.coffee.shop.domain.models

import android.os.Parcelable
import com.coffee.shop.data.models.response.Coffee
import kotlinx.parcelize.Parcelize

@Parcelize
data class DomainHomeData(
    val user: DomainUser,
    val promoUrl: String? = null,
    val coffeeCategories: List<Coffee>
) : Parcelable