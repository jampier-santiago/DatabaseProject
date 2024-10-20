package com.example.databaseproject.Repository

import com.example.databaseproject.DAO.AutorDAO
import com.example.databaseproject.DAO.LibroDAO
import com.example.databaseproject.DAO.MiembroDAO
import com.example.databaseproject.DAO.PrestamoDAO
import com.example.databaseproject.Model.*
import com.example.databaseproject.POJO.LibroConAutor
import com.example.databaseproject.POJO.PrestamoConDetalles

class BibliotecaRepository(
    private val autorDao: AutorDAO,
    private val libroDao: LibroDAO,
    private val miembroDao: MiembroDAO,
    private val prestamoDao: PrestamoDAO
) {
    // Operaciones para Autores
    suspend fun insertAutor(autor: Autor) {
        autorDao.insert(autor)
    }

    suspend fun getAllAutores(): List<Autor> {
        return autorDao.getAllAutores()
    }

    suspend fun deleteAutorById(autorId: Int): Int {
        return autorDao.deleteById(autorId)
    }

    // Operaciones para Libros
    suspend fun insertLibro(libro: Libro) {
        libroDao.insert(libro)
    }

    suspend fun getAllLibros(): List<Libro> {
        return libroDao.getAllLibros()
    }

    // Operaciones para Miembros
    suspend fun insertMiembro(miembro: Miembro) {
        miembroDao.insert(miembro)
    }

    suspend fun getAllMiembros(): List<Miembro> {
        return miembroDao.getAllMiembros()
    }

    // Operaciones para Préstamos
    suspend fun insertPrestamo(prestamo: Prestamo) {
        prestamoDao.insert(prestamo)
    }

    suspend fun obtenerPrestamosConDetalles(): List<PrestamoConDetalles> {
        return prestamoDao.obtenerPrestamosConDetalles()
    }
}
