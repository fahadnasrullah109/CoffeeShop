package com.coffee.shop.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DomainUser(
    val userId: String, val name: String
) : Parcelable