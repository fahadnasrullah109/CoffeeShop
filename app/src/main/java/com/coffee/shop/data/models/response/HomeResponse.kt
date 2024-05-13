package com.coffee.shop.data.models.response

import android.os.Parcelable
import com.coffee.shop.data.models.db.User
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class HomeResponse(
    @SerializedName("user") val user: User,
    @SerializedName("promoUrl") val promoUrl: String? = null,
    @SerializedName("coffeeCategories") val coffeeCategories: List<CoffeeCategory>
) : Parcelable