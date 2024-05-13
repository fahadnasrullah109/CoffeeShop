package com.coffee.shop.data.models.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CoffeeCategory(
    @SerializedName("category")
    val category: String,
    @SerializedName("items")
    val items: List<Coffee>,
) : Parcelable