package com.example.purrse.repo

import com.example.purrse.data.UserDao
import com.example.purrse.model.User
import kotlinx.coroutines.flow.Flow

class UserRepo(private val userDao: UserDao) {

    suspend fun insertUser(user: User){
        userDao.insertUser(user)
    }

    suspend fun login(username: String, password: String): User?{
        return userDao.login(username, password)
    }

    suspend fun getUserByID(userId: Int):User?{
        return userDao.getUserIdBy(userId)
    }

    suspend fun deleteUser(user: User){
        userDao.deleteUser(user)
    }

}