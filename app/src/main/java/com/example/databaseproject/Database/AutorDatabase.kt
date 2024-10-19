package com.example.databaseproject.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.databaseproject.DAO.AutorDAO
import com.example.databaseproject.Model.Autor

@Database(entities = [Autor::class], version = 1, exportSchema = false)
abstract class AutorDatabase: RoomDatabase() {
    abstract fun autorDao(): AutorDAO

    companion object {
        @Volatile
        private var INSTANCE: AutorDatabase? = null

        fun getDatabase(context: Context): AutorDatabase {
            return  INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AutorDatabase::class.java,
                    "autor_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}