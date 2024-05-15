package com.coffee.shop.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coffee.shop.data.models.db.Coffee

@Dao
interface CoffeeDao {

    @Query("SELECT * FROM coffee")
    suspend fun getAllFavouriteCoffees(): List<Coffee>

    @Query("SELECT * FROM coffee WHERE id = :coffeeId")
    suspend fun isFavourite(coffeeId: String): Coffee?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(coffee: Coffee): Long

    @Query("DELETE FROM coffee WHERE id = :coffeeId")
    suspend fun removeCoffeeById(coffeeId: String)

    @Query("DELETE FROM coffee")
    suspend fun clear()

}