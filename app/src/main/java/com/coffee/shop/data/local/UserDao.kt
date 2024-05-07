package com.coffee.shop.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coffee.shop.data.models.db.User

@Dao
interface UserDao {

    @Query("SELECT * FROM User ORDER BY userId DESC LIMIT 1")
    suspend fun getLoggedInUser(): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User): Long

    @Query("DELETE FROM USER")
    suspend fun clear()

}