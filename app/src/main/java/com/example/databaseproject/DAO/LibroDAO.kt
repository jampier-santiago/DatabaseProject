package com.example.databaseproject.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

import com.example.databaseproject.Model.Libro
import com.example.databaseproject.POJO.LibroConAutor

@Dao
interface LibroDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(libro: Libro)

    @Query("""
        SELECT libros.id, libros.titulo, libros.genero, libros.autorId, 
               autores.nombre AS nombreAutor, autores.apellido AS apellidoAutor
        FROM libros
        INNER JOIN autores ON libros.autorId = autores.id
    """)
    suspend fun getAllLibrosConAutores(): List<LibroConAutor>

    @Update
    suspend fun update(libro: Libro): Int

    @Query("DELETE FROM libros WHERE id = :libroId")
    suspend fun deleteById(libroId: Int): Int
}
