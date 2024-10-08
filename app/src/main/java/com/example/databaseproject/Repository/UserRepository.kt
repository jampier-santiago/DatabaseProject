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

    suspend fun deleteById(userId: Int): Int {
        return userDao.deleteById(userId) //llama al metodo deleteById del DAO
    }

    suspend fun updateUser(user: User): Int {
        return userDao.update(user) // llama al método update del DAO
    }
}

