package com.example.purrse.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.purrse.model.Category
import com.example.purrse.model.Expense
import com.example.purrse.repo.CategoryRepo
import com.example.purrse.repo.ExpenseRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddExpenseViewModel(
    private val expenseRepo: ExpenseRepo,
    private val categoryRepo: CategoryRepo
) : ViewModel() {

    val amount = MutableStateFlow("")
    val date = MutableStateFlow("")
    val description = MutableStateFlow("")
    val receiptUri = MutableStateFlow("")
    val categoryName = MutableStateFlow("")

    private val _saveState = MutableStateFlow(false)
    val saveState: StateFlow<Boolean> = _saveState.asStateFlow()

    fun saveExpense(userId: Int) {
        viewModelScope.launch {
            val amountText = amount.value.trim()
            val category = categoryName.value.trim()
            val dateValue = date.value.trim()
            val descriptionValue = description.value.trim()
            val receipt = receiptUri.value.trim()

            if (amountText.isBlank() || category.isBlank() || dateValue.isBlank()) {
                _saveState.value = false
                return@launch
            }

            val amountValue = amountText.toDoubleOrNull()
            if (amountValue == null || amountValue <= 0) {
                _saveState.value = false
                return@launch
            }

            // Check for existing category or insert a new one
            val existing = categoryRepo.getCategoriesForUser(userId)
                .find { it.name.equals(category, ignoreCase = true) }

            val categoryId = if (existing != null) {
                existing.categoryId
            } else {
                val newCategory = Category(userId = userId, name = category)
                categoryRepo.insertCategory(newCategory)

                // Get newly inserted category ID
                categoryRepo.getCategoriesForUser(userId)
                    .find { it.name.equals(category, ignoreCase = true) }
                    ?.categoryId ?: 0
            }

            val expense = Expense(
                userId = userId,
                amount = amountValue,
                categoryId = categoryId,
                date = dateValue,
                description = descriptionValue,
                receipt = receipt
            )

            expenseRepo.insertExpense(expense)
            _saveState.value = true
        }
    }
}
