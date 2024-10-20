package com.example.databaseproject.Repository

import com.example.databaseproject.DAO.LibroDAO
import com.example.databaseproject.Model.Libro
import com.example.databaseproject.POJO.LibroConAutor


class LibroRepository(private val libroDao: LibroDAO) {
    suspend fun insert(libro: Libro) {
        libroDao.insert(libro)
    }

    suspend fun getAllLibros(): List<LibroConAutor> {
        return libroDao.getAllLibrosConAutores()
    }

    suspend fun deleteById(libroId: Int): Int {
        return libroDao.deleteById(libroId)
    }

    suspend fun update(libro: Libro): Int {
        return libroDao.update(libro)
    }
}
