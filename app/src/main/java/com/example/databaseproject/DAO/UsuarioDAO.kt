package com.example.DatabaseProject.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.DatabaseProject.Model.User

@Dao

interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user : User)

    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<User>
}