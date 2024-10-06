package com.example.DatabaseProject.Repository

import com.example.DatabaseProject.DAO.UserDAO
import com.example.DatabaseProject.Model.User

class UserRepository(private val userDao: UserDAO) {
    suspend fun insert(user:User){
        userDao.insert(user)
    }
    suspend fun getAllUser(): List<User>{
        return  userDao.getAllUsers()
    }
}

