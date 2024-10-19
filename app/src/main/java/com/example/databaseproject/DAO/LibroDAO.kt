package com.example.databaseproject.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.databaseproject.Model.Libro

@Dao

interface  LibroDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(libro: Libro)

    @Query("SELECT * FROM libros")
    suspend fun getAllLibros(): List<Libro>

    @Query("""
        SELECT libros.titulo, libros.genero, autores.nombre, autores.apellido 
        FROM libros 
        INNER JOIN autores ON libros.autorId = autores.id
    """)
    suspend fun obtenerLibrosConAutores(): List<Libro>

    @Query("DELETE FROM libros where id = :libroId")
    suspend fun deleteById(libroId: Int): Int

    @Update
    suspend fun update(libro: Libro): Int
}