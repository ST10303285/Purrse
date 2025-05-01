package com.example.purrse.repo

import com.example.purrse.data.GoalsDao
import com.example.purrse.model.Goal
import kotlinx.coroutines.flow.Flow

class GoalRepo(private val goalsDao: GoalsDao) {

    suspend fun insertGoal(goal: Goal){
        goalsDao.insertGoal(goal)
    }

    suspend fun getGoalForMonth(userId: Int, month: String):Goal?{
        return goalsDao.getGoalForMonth(userId, month)
    }

    suspend fun
}