package com.example.databaseproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.DatabaseProject.DAO.UserDAO
import com.example.DatabaseProject.Database.UserDatabase
import com.example.DatabaseProject.Repository.UserRepository
import com.example.DatabaseProject.Screen.UserApp

class MainActivity : ComponentActivity() {
    private lateinit var userRepository: UserRepository
    private lateinit var userDAO: UserDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = UserDatabase.getDatabase(applicationContext)
        userDAO = db.userDao()
        userRepository = UserRepository(userDAO)

        enableEdgeToEdge()
        setContent {
         UserApp(userRepository)
        }
    }
}

