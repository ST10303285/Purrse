package com.example.purrse.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.purrse.model.Goal
import com.example.purrse.repo.GoalRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GoalViewModel (private val goalRepo: GoalRepo) : ViewModel() {


    private val _goalState = MutableStateFlow<Goal?>(null)
    val goalState: StateFlow<Goal?> = _goalState

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun fetchGoal(userId: Int, month: String) {
        viewModelScope.launch {
            try {
                val goal = goalRepo.getGoalForMonth(userId, month)
                _goalState.value = goal
            } catch (e: Exception) {
                _errorMessage.value = "Error fetching goal: ${e.localizedMessage}"
            }
        }
    }

    fun saveGoal(goal: Goal) {
        viewModelScope.launch {
            try {
                goalRepo.insertGoal(goal)
                _goalState.value = goal
            } catch (e: Exception) {
                _errorMessage.value = "Error saving goal: ${e.localizedMessage}"
            }
        }
    }

    fun updateGoal(goal: Goal) {
        viewModelScope.launch {
            try {
                goalRepo.updateGoal(goal)
                _goalState.value = goal
            } catch (e: Exception) {
                _errorMessage.value = "Error updating goal: ${e.localizedMessage}"
            }
        }
    }

    fun deleteGoal(goal: Goal) {
        viewModelScope.launch {
            try {
                goalRepo.deleteGoal(goal)
                _goalState.value = null
            } catch (e: Exception) {
                _errorMessage.value = "Error deleting goal: ${e.localizedMessage}"
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}