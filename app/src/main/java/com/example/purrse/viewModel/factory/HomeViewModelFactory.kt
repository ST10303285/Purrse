package com.example.purrse.viewModel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.purrse.data.ExpenseDao
import com.example.purrse.data.GoalsDao
import com.example.purrse.repo.ExpenseRepo
import com.example.purrse.repo.GoalRepo
import com.example.purrse.repo.UserRepo
import com.example.purrse.viewModel.HomeViewModel

class HomeViewModelFactory (private val expenseDao: ExpenseDao, private val goalDao: GoalsDao, private val userId: Int) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(expenseDao, goalDao, userId) as T
        }else{
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}}