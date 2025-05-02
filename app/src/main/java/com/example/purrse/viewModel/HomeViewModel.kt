package com.example.purrse.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.purrse.repo.ExpenseRepo
import com.example.purrse.repo.CategoryRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.purrse.model.Goal
import com.example.purrse.repo.GoalRepo
import com.example.purrse.repo.UserRepo
import com.example.purrse.data.CategorySpending
import com.example.purrse.data.ExpenseDao
import com.example.purrse.data.GoalsDao
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class HomeViewModel(
    private val expenseDao: ExpenseDao,
    private val goalDao: GoalsDao,
    private val userId: Int // pass logged-in user's ID
) : ViewModel() {

    private val _balance = MutableLiveData<Double>()
    val balance: LiveData<Double> = _balance

    private val _income = MutableLiveData<Double>()
    val income: LiveData<Double> = _income

    private val _expense = MutableLiveData<Double>()
    val expense: LiveData<Double> = _expense

    private val _budgetPercentage = MutableLiveData<Int>()
    val budgetPercentage: LiveData<Int> = _budgetPercentage

    private val _topCategories = MutableLiveData<List<CategorySpending>>()
    val topCategories: LiveData<List<CategorySpending>> = _topCategories

    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> = _uiState

    val currentMonth: String
        get() = SimpleDateFormat("MMMM yyyy", Locale.getDefault()).format(Date())

    init {
        loadSummary()
    }

    fun refreshSummary() {
        loadSummary()
    }

    private fun loadSummary() = viewModelScope.launch {
        val totalIncome = goalDao.getTotalIncome(userId) ?: 0.0
        val totalExpense = expenseDao.getTotalExpenses(userId) ?: 0.0
        val calculatedBalance = totalIncome - totalExpense
        val percentage = if (totalIncome > 0) ((totalExpense / totalIncome) * 100).toInt() else 0


        _income.postValue(totalIncome)
        _expense.postValue(totalExpense)
        _balance.postValue(calculatedBalance)
        _budgetPercentage.postValue(percentage)

        }
    sealed class UiState {
        object Loading : UiState()
        object Success : UiState()
        data class Error(val message: String) : UiState()
    }
}

