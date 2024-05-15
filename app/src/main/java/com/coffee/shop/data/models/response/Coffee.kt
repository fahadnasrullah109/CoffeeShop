package com.coffee.shop.data.models.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Coffee(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("price_small")
    val priceSmall: Double,
    @SerializedName("price_medium")
    val priceMedium: Double,
    @SerializedName("price_large")
    val priceLarge: Double,
    @SerializedName("rating")
    val rating: Double,
    @SerializedName("detail")
    val detail: String,
    @SerializedName("image")
    val image: String,
) : Parcelable