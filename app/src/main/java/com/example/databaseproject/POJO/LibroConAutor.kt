package com.example.databaseproject.POJO

data class LibroConAutor(
    val id: Int,
    val titulo: String,
    val genero: String,
    val autorId: Int,
    val nombreAutor: String,
    val apellidoAutor: String
)