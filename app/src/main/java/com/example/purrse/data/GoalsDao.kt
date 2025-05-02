package com.example.purrse.data

import androidx.room.*
import com.example.purrse.model.Expense
import com.example.purrse.model.Goal

@Dao
interface GoalsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGoal(goal: Goal):Long

    @Query("SELECT * FROM goals WHERE userId = :userId AND month =:month")
    suspend fun getGoalForMonth(userId: Int, month: String): Goal?

    @Update
    suspend fun  updateGoal(goal : Goal)

    @Delete
    suspend fun deleteGoal(goal: Goal)

    @Query("SELECT SUM(amount) FROM goals WHERE userId = :userId")
    suspend fun getTotalIncome(userId: Int): Double?
}

