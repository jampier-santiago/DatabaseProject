package com.example.databaseproject.Model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(tableName = "libros",
    foreignKeys = [ForeignKey(
        entity = Autor::class,
        parentColumns = ["id"],
        childColumns = ["autorId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Libro (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val titulo: String,
    val genero: String,
    val autorId: Int
)