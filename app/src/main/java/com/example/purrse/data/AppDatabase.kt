package com.example.purrse.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
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
    version = 3,
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

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {

                db.execSQL("""
                    CREATE TABLE IF NOT EXISTS `goals` (
                      `goalId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                      `userId` INTEGER NOT NULL,
                      `minGoal` REAL,
                      `maxGoal` REAL NOT NULL,
                      `month` TEXT NOT NULL,
                      `amount` REAL NOT NULL,
                      `title` TEXT NOT NULL,
                      `targetDate` TEXT NOT NULL,
                      FOREIGN KEY(`userId`) REFERENCES `user`(`userId`) ON DELETE CASCADE
                    )
                """.trimIndent())
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_goals_userId` ON `goals` (`userId`)")

            }
        }

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(context, AppDatabase::class.java, "purrse_db")
                    .fallbackToDestructiveMigration()
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            db.execSQL("PRAGMA foreign_keys=ON;")


                        }
                    })
                    .build()
                    .also { INSTANCE = it }
            }
        }}}

