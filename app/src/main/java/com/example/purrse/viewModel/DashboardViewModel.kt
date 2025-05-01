package com.example.purrse.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.purrse.repo.ExpenseRepo
import com.example.purrse.repo.CategoryRepo
import com.example.purrse.repo.GoalRepo
import com.example.purrse.data.CategorySpending
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DashboardViewModel( private val expenseRepo: ExpenseRepo, private val categoryRepo: CategoryRepo )  : ViewModel(){

    private val _categorySpending = MutableStateFlow<Map<String, Double>>(emptyMap())
    val categorySpending: StateFlow<Map<String, Double>> = _categorySpending


    private val _totalSpending = MutableStateFlow<Double>(0.0)
    val totalSpending: StateFlow<Double> = _totalSpending

    init {

        fetchTotalSpending()
    }



    private fun fetchTotalSpending() {
        viewModelScope.launch {
            // Assuming you can fetch the total spending from a repository
            val total = expenseRepo.getTotalSpending()
            _totalSpending.value = total
        }
    }
}

