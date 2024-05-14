package com.coffee.shop.data.models.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Order(
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("price")
    val price: Double,
    @SerializedName("quantity")
    val quantity: Int,
    @SerializedName("rating")
    val rating: Double,
    @SerializedName("date")
    val date: String,
    @SerializedName("image")
    val image: String,
) : Parcelable