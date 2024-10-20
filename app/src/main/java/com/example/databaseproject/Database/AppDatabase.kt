package com.example.DatabaseProject.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import com.example.databaseproject.DAO.AutorDAO
import com.example.databaseproject.DAO.LibroDAO
import com.example.databaseproject.DAO.MiembroDAO
import com.example.databaseproject.DAO.PrestamoDAO

import com.example.databaseproject.Model.Autor
import com.example.databaseproject.Model.Libro
import com.example.databaseproject.Model.Miembro
import com.example.databaseproject.Model.Prestamo

// Definir todas las entidades en la anotación @Database
@Database(entities = [Autor::class, Libro::class, Miembro::class, Prestamo::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    // Definir todos los DAO
    abstract fun autorDao(): AutorDAO
    abstract fun libroDao(): LibroDAO
    abstract fun miembroDao(): MiembroDAO
    abstract fun prestamoDao(): PrestamoDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
