package com.example.databaseproject.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.databaseproject.Model.Miembro

@Database(entities = [Miembro::class], version = 1, exportSchema = false)
abstract class MiembroDatabase: RoomDatabase() {
    abstract fun miebroDao(): MiembroDAO

    companion object{
        @Volatile
        private var INSTANCE: MiembroDatabase? = null

        fun getDatabase(context: Context): MiembroDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MiembroDatabase::class.java,
                    "miembro_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}