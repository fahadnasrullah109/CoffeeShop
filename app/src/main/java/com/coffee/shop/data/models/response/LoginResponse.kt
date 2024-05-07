package com.coffee.shop.data.models.response

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    @SerializedName("token")
    val token: String,
    @SerializedName("ID")
    val ID: String?,
    @SerializedName("firstName")
    val firstName: String?,
    @SerializedName("lastName")
    val lastName: String?,
    @SerializedName("email")
    val email: String?
)