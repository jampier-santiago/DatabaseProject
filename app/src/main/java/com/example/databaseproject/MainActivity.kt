package com.example.databaseproject

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.People

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

import com.example.DatabaseProject.Database.AppDatabase
import com.example.databaseproject.Screen.MiembroApp

import com.example.databaseproject.Repository.AutorRepository
import com.example.databaseproject.Repository.LibroRepository
import com.example.databaseproject.Repository.MiembroRepository
import com.example.databaseproject.Repository.PrestamoRepository
import com.example.databaseproject.Screen.AutorApp
import com.example.databaseproject.Screen.LibroApp



class MainActivity : ComponentActivity() {

    // Repositorios
    private lateinit var autorRepository: AutorRepository
    private lateinit var libroRepository: LibroRepository
    private lateinit var miembroRepository: MiembroRepository
    private lateinit var prestamoRepository: PrestamoRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializamos la base de datos y los DAO
        val db = AppDatabase.getDatabase(applicationContext)

        // Inicializamos los DAO
        val autorDAO = db.autorDao()
        val libroDAO = db.libroDao()
        val miembroDAO = db.miembroDao()
        val prestamoDAO = db.prestamoDao()

        // Inicializamos los repositorios
        autorRepository = AutorRepository(autorDAO)
        libroRepository = LibroRepository(libroDAO)
        miembroRepository = MiembroRepository(miembroDAO)
        prestamoRepository = PrestamoRepository(prestamoDAO)

        enableEdgeToEdge()

        setContent {
            MiembroApp(miembroRepository)
        }

        // Configuramos el contenido usando Jetpack Compose
        setContent {
            MyApp(
                autorRepository = autorRepository,
                libroRepository = libroRepository,
                miembroRepository = miembroRepository,
            )
        }
    }
}


@Composable
fun NavigationHost(
    navController: NavHostController,
    autorRepository: AutorRepository,
    libroRepository: LibroRepository,
    miembroRepository: MiembroRepository
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Autores.route
    ) {
        composable(BottomNavItem.Autores.route) {
            AutorApp(autorRepository)
        }
        composable(BottomNavItem.Libros.route) {
            LibroApp(libroRepository, autorRepository)
        }
        composable(BottomNavItem.Miembros.route) {
            MiembroApp(miembroRepository)
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MyApp(
    autorRepository: AutorRepository,
    libroRepository: LibroRepository,
    miembroRepository: MiembroRepository
) {
    val navController = rememberNavController() // Controlador de navegación

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) } // Barra de navegación inferior
    ) {
        NavigationHost(
            navController = navController,
            autorRepository = autorRepository,
            libroRepository = libroRepository,
            miembroRepository = miembroRepository
        )
    }
}

sealed class BottomNavItem(val title: String, val icon: ImageVector, val route: String) {
    object Autores : BottomNavItem("Autores", Icons.Default.Person, "autores")
    object Libros : BottomNavItem("Libros", Icons.Default.Book, "libros")
    object Miembros : BottomNavItem("Miembros", Icons.Default.People, "miembros")
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem.Autores,
        BottomNavItem.Libros,
        BottomNavItem.Miembros
    )

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                label = { Text(text = item.title) },
                selected = false, // Aquí puedes manejar el estado seleccionado si lo deseas
                onClick = {
                    navController.navigate(item.route)
                }
            )
        }
    }
}
