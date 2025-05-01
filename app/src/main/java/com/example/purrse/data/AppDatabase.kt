package com.example.purrse.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.purrse.model.User
import com.example.purrse.model.Goal
import com.example.purrse.model.Expense
import com.example.purrse.model.Category
import com.example.purrse.data.UserDao
import com.example.purrse.data.ExpenseDao
import com.example.purrse.data.GoalsDao
import com.example.purrse.data.CategoryDao
import com.example.purrse.data.CategorySpending
import kotlin.concurrent.Volatile

@Database(
    entities = [User::class, Category::class, Expense::class, Goal::class],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase: RoomDatabase(){

    abstract fun userDao(): UserDao
    abstract fun categoryDao(): CategoryDao
    abstract fun expenseDao(): ExpenseDao
    abstract fun goalDao(): GoalsDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{
            return INSTANCE?: synchronized(this){
                val instance= Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "purrse_db"
                ).build()
                INSTANCE = instance
                instance
            }

        }
    }
}


