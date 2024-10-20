package com.example.databaseproject.Repository

import com.example.databaseproject.DAO.PrestamoDAO
import com.example.databaseproject.Model.Prestamo
import com.example.databaseproject.POJO.PrestamoConDetalles

class PrestamoRepository(private val prestamoDao: PrestamoDAO) {
    suspend fun insert(prestamo: Prestamo) {
        prestamoDao.insert(prestamo)
    }

    suspend fun getAllPrestamos(): List<Prestamo> {
        return prestamoDao.getAllPrestamos()
    }

    suspend fun deleteById(prestamoId: Int): Int {
        return prestamoDao.deleteById(prestamoId)
    }

    suspend fun update(prestamo: Prestamo): Int {
        return prestamoDao.update(prestamo)
    }

    suspend fun obtenerPrestamosConDetalles(): List<PrestamoConDetalles> {
        return prestamoDao.obtenerPrestamosConDetalles()
    }
}
