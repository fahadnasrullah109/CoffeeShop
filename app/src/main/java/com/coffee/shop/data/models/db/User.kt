package com.coffee.shop.data.models.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "user")
data class User(
    @PrimaryKey var userId: String,
    val name: String,
    val profilePicture: String,
    val location: String,
) : Parcelable