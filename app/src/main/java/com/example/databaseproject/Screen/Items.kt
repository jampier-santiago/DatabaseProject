package com.example.DatabaseProject.Screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.DatabaseProject.DAO.UserDAO
import com.example.DatabaseProject.Model.User
import com.example.DatabaseProject.Repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun UserApp(userRepository: UserRepository) {
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    var isEditMode by rememberSaveable { mutableStateOf(false) }
    var showDeleteDialog by rememberSaveable { mutableStateOf(false) }
    var userToDelete by rememberSaveable { mutableStateOf<User?>(null) }
    var id by rememberSaveable { mutableStateOf("") }
    var users by rememberSaveable { mutableStateOf(listOf<User>()) }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
    ) {
        TextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text(text = "Nombre") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = apellido,
            onValueChange = { apellido = it },
            label = { Text(text = "Apellido") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = edad,
            onValueChange = { input ->
                if (input.all { it.isDigit() }) {
                    edad = input
                }
            },
            label = { Text(text = "Edad") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            if (nombre.isBlank()) {
                Toast.makeText(context, "El nombre no puede estar vacío", Toast.LENGTH_SHORT).show()
                return@Button
            }

            if (apellido.isBlank()) {
                Toast.makeText(context, "El apellido no puede estar vacío", Toast.LENGTH_SHORT).show()
                return@Button
            }

            val edadInt = edad.toIntOrNull()
            if (edadInt == null || edadInt <= 0) {
                Toast.makeText(context, "La edad debe ser un número mayor que cero", Toast.LENGTH_SHORT).show()
                return@Button
            }

            val user = User(
                nombre = nombre,
                apellido = apellido,
                edad = edadInt
            )

            scope.launch {
                withContext(Dispatchers.IO) {
                    if (isEditMode) {
                        userRepository.updateUser(user)
                        isEditMode = false
                    } else {
                        userRepository.insert(user)
                    }
                }
                Toast.makeText(
                    context,
                    if (isEditMode) "Usuario Actualizado" else "Usuario Registrado",
                    Toast.LENGTH_SHORT
                ).show()
                clearFields(
                    onClear = {
                        nombre = ""
                        apellido = ""
                        edad = ""
                        id = ""
                    }
                )
                users = withContext(Dispatchers.IO) {
                    userRepository.getAllUser()
                }
            }
        }) {
            Text(text = "Registrar")
        }

        Spacer(modifier = Modifier.height(8.dp))

        var users by remember {
            mutableStateOf(listOf<User>())
        }

        Button(onClick = {
            scope.launch {
                users = withContext(Dispatchers.IO) {
                    userRepository.getAllUser()
                }
            }
        }) {
            Text(text = "Listar")
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(users.size) { index ->
                val user = users[index]
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
                            Text(text = "ID: ${user.id}")
                            Text(text = "Nombre: ${user.nombre}")
                            Text(text = "Apellido: ${user.apellido}")
                            Text(text = "Edad: ${user.edad}")
                        }
                        Row {
                            // Icono para editar
                            IconButton(onClick = {
                                nombre = user.nombre
                                apellido = user.apellido
                                edad = user.edad.toString()
                                id = user.id.toString()
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
                                userToDelete = user
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

        if (showDeleteDialog) {
            AlertDialog(
                onDismissRequest = { showDeleteDialog = false },
                title = { Text(text = "Confirmar Eliminación") },
                text = { Text(text = "¿Estás seguro de que deseas eliminar este usuario?") },
                confirmButton = {
                    Button(
                        onClick = {
                            userToDelete?.let { user ->
                                scope.launch {
                                    withContext(Dispatchers.IO) {
                                        userRepository.deleteById(user.id)
                                    }
                                    Toast.makeText(context, "Usuario Eliminado", Toast.LENGTH_SHORT).show()
                                    users = withContext(Dispatchers.IO) {
                                        userRepository.getAllUser()
                                    }
                                }
                            }
                            showDeleteDialog = false
                            clearFields(
                                onClear = {
                                    nombre = ""
                                    apellido = ""
                                    edad = ""
                                    id = ""
                                }
                            )
                        }
                    ) {
                        Text(text = "Eliminar")
                    }
                },
                dismissButton = {
                    Button(onClick = { showDeleteDialog = false }) {
                        Text(text = "Cancelar")
                    }
                }
            )
        }
    }
}

fun clearFields(onClear: () -> Unit) {
    onClear()
}

