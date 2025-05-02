package com.example.purrse.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.purrse.data.CategorySpending
import com.example.purrse.data.ExpenseDao
import com.example.purrse.model.Expense
import com.example.purrse.repo.ExpenseRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TrendsViewModel(
    private val dao: ExpenseDao
) : ViewModel() {
    private val _expenses = MutableLiveData<List<Expense>>()
    val expenses: LiveData<List<Expense>> = _expenses

    private val _totals = MutableLiveData<List<CategorySpending>>()
    val totals: LiveData<List<CategorySpending>> = _totals

    fun load(userId: Int, from: String, to: String) = viewModelScope.launch {
        _expenses.postValue(dao.getExpensesForUserInRange(userId, from, to))
        _totals.postValue(dao.getTotalPerCategory(userId, from, to))
    }
}
