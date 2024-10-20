package com.example.databaseproject.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

import com.example.databaseproject.Model.Autor

@Dao

interface AutorDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(autor: Autor)

    @Query("SELECT * FROM autores")
    suspend fun getAllAutores(): List<Autor>

    @Query("DELETE FROM autores WHERE id = :autorId")
    suspend fun deleteById(autorId: Int): Int

    @Update
    suspend fun update(autor: Autor): Int
}