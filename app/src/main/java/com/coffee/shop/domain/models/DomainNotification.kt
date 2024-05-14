package com.coffee.shop.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DomainNotification(
    val type: String,
    val title: String,
    val message: String,
    val date: String,
) : Parcelable