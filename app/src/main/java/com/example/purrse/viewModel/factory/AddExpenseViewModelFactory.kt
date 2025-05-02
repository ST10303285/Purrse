package com.example.purrse.viewModel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.purrse.data.ExpenseDao
import com.example.purrse.repo.CategoryRepo
import com.example.purrse.repo.ExpenseRepo
import com.example.purrse.viewModel.AddExpenseViewModel

class AddExpenseViewModelFactory(
    private val expenseRepo: ExpenseRepo,
    private val categoryRepo: CategoryRepo
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddExpenseViewModel::class.java)) {
            return AddExpenseViewModel(expenseRepo, categoryRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}