package com.example.purrse.data

import androidx.room.*
import com.example.purrse.model.Expense
import com.example.purrse.model.User

@Dao
interface UserDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User):Long

    @Query("SELECT * FROM user WHERE username = :username AND password = :password")
    suspend fun login(username: String, password: String): User?

    @Query("SELECT * FROM user WHERE userId = :userId")
    suspend fun getUserIdBy(userId: Int): User?

    @Delete
    suspend fun deleteUser(user: User)
}
