package com.coffee.shop.data.models.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "coffee")
data class Coffee(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val priceSmall: Double,
    val priceMedium: Double,
    val priceLarge: Double,
    val rating: Double,
    val detail: String,
    val image: String,
) : Parcelable