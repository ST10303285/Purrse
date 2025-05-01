package com.example.purrse.data

import androidx.room.*
import com.example.purrse.model.Goal

@Dao
interface GoalsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGoal(goal: Goal):Long

    @Query("SELECT * FROM goals WHERE userId AND month =:month")
    suspend fun getGoalForMonth(userId: Int, month: String): Goal?

    @Update
    suspend fun  updateGoal(goal : Goal)
}

