package com.example.purrse.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.purrse.model.Expense
import com.example.purrse.repo.ExpenseRepo
import com.example.purrse.data.CategorySpending
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class ExpenseViewModel(private val expenseRepo: ExpenseRepo): ViewModel() {

    private val _expenses = MutableStateFlow<List<Expense>>(emptyList())
    val expenses: StateFlow<List<Expense>> = _expenses

    private val _categorySpendingList = MutableStateFlow<List<CategorySpending>>(emptyList())
    val categorySpendingList: StateFlow<List<CategorySpending>> = _categorySpendingList

    private val _totalSpending = MutableStateFlow(0.0)
    val totalSpending: StateFlow<Double> = _totalSpending

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun fetchExpensesForUser(userId: Int, startDate: String, endDate: String) {
        viewModelScope.launch {
            try {
                val expenses = expenseRepo.getExpenseForUserInRange(userId, startDate, endDate)
                _expenses.value = expenses
            } catch (e: Exception) {
                _error.value = "Failed to fetch expenses: ${e.localizedMessage}"
            }
        }
    }

    fun calculateTotalSpending() {
        viewModelScope.launch {
            try {
                val total = expenseRepo.getTotalSpending()
                _totalSpending.value = total
            } catch (e: Exception) {
                _error.value = "Error calculating total spending: ${e.localizedMessage}"
            }
        }
    }

    fun fetchCategorySpending(userId: Int, startDate: String, endDate: String) {
        viewModelScope.launch {
            try {
                val categorySpending = expenseRepo.getTotalPerCategory(userId, startDate, endDate)
                _categorySpendingList.value = categorySpending
            } catch (e: Exception) {
                _error.value = "Failed to fetch category spending: ${e.localizedMessage}"
            }
        }
    }

    fun insertExpense(expense: Expense) {
        viewModelScope.launch {
            try {
                expenseRepo.insertExpense(expense)
                calculateTotalSpending()
            } catch (e: Exception) {
                _error.value = "Failed to insert expense: ${e.localizedMessage}"
            }
        }
    }

    fun deleteExpense(expense: Expense) {
        viewModelScope.launch {
            try {
                expenseRepo.deleteExpense(expense)
                calculateTotalSpending()
            } catch (e: Exception) {
                _error.value = "Failed to delete expense: ${e.localizedMessage}"
            }
        }
    }

    fun clearError() {
        _error.value = null
    }
}