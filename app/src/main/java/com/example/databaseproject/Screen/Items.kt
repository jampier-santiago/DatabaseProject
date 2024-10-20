package com.example.databaseproject.Screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.databaseproject.BottomNavigationBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

import com.example.databaseproject.Model.Miembro
import com.example.databaseproject.Repository.MiembroRepository

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MiembroApp(miembroRepository: MiembroRepository) {
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var fechaInscripcion by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    var isEditMode by rememberSaveable { mutableStateOf(false) }
    var showDeleteDialog by rememberSaveable { mutableStateOf(false) }
    var miembroToDelete by rememberSaveable { mutableStateOf<Miembro?>(null) }
    var id by rememberSaveable { mutableStateOf("") }
    var miembros by rememberSaveable { mutableStateOf(listOf<Miembro>()) }

    val context = LocalContext.current

    val navController = rememberNavController() // Crear NavController

    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController) } // Pasamos el navController a BottomNavigationBar
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Aplicamos el padding del Scaffold
                .padding(16.dp) // Padding adicional
        ) {
            // Campo de entrada para el nombre del miembro
            TextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text(text = "Nombre") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            // Campo de entrada para el apellido del miembro
            TextField(
                value = apellido,
                onValueChange = { apellido = it },
                label = { Text(text = "Apellido") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            // Campo de entrada para la fecha de inscripción del miembro
            TextField(
                value = fechaInscripcion,
                onValueChange = { fechaInscripcion = it },
                label = { Text(text = "Fecha de Inscripción") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp)) // Espacio adicional antes de los botones

            // Botón para registrar o actualizar un miembro
            Button(
                onClick = {
                    if (nombre.isBlank()) {
                        Toast.makeText(context, "El nombre no puede estar vacío", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    if (apellido.isBlank()) {
                        Toast.makeText(context, "El apellido no puede estar vacío", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    if (fechaInscripcion.isBlank()) {
                        Toast.makeText(context, "La fecha de inscripción no puede estar vacía", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    val miembro = Miembro(
                        nombre = nombre,
                        apellido = apellido,
                        fecha_inscripcion = fechaInscripcion
                    )

                    scope.launch {
                        withContext(Dispatchers.IO) {
                            if (isEditMode) {
                                miembroRepository.update(miembro)
                                isEditMode = false
                            } else {
                                miembroRepository.insert(miembro)
                            }
                        }
                        Toast.makeText(
                            context,
                            if (isEditMode) "Miembro Actualizado" else "Miembro Registrado",
                            Toast.LENGTH_SHORT
                        ).show()
                        clearFields(
                            onClear = {
                                nombre = ""
                                apellido = ""
                                fechaInscripcion = ""
                                id = ""
                            }
                        )
                        miembros = withContext(Dispatchers.IO) {
                            miembroRepository.getAllMiembros()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(text = if (isEditMode) "Actualizar" else "Registrar")
            }

            // Botón para listar los miembros
            Button(
                onClick = {
                    scope.launch {
                        miembros = withContext(Dispatchers.IO) {
                            miembroRepository.getAllMiembros()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(text = "Listar Miembros")
            }

            Spacer(modifier = Modifier.height(16.dp)) // Espacio antes de la lista de miembros

            // Scroll para la lista de miembros
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f) // Permite que LazyColumn tome el espacio restante y sea desplazable
            ) {
                items(miembros.size) { index ->
                    val miembro = miembros[index]
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(text = "ID: ${miembro.id}")
                                Text(text = "Nombre: ${miembro.nombre}")
                                Text(text = "Apellido: ${miembro.apellido}")
                                Text(text = "Fecha de Inscripción: ${miembro.fecha_inscripcion}")
                            }
                            Row {
                                // Icono para editar
                                IconButton(onClick = {
                                    nombre = miembro.nombre
                                    apellido = miembro.apellido
                                    fechaInscripcion = miembro.fecha_inscripcion
                                    id = miembro.id.toString()
                                    isEditMode = true
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = "Editar",
                                        tint = Color.Green
                                    )
                                }

                                // Icono para borrar
                                IconButton(onClick = {
                                    miembroToDelete = miembro
                                    showDeleteDialog = true
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Borrar",
                                        tint = Color.Red
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
fun clearFields(onClear: () -> Unit) {
    onClear()
}