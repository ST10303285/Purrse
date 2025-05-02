package com.example.purrse.viewModel

import com.example.purrse.CategorySelectionActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.purrse.model.Category
import com.example.purrse.repo.CategoryRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class CategoryViewModel(private val categoryRepo: CategoryRepo,  private val userId: Int): ViewModel() {

    val allCategories = MutableStateFlow<List<Category>>(emptyList())
    val query = MutableStateFlow("")

    init {
        viewModelScope.launch {
            // whenever the query changes, we re‐fetch that user’s categories
            query.collect { q ->
                val fullList = categoryRepo.getCategoriesForUser(userId)
                allCategories.value = if (q.isBlank()) {
                    fullList
                } else {
                    fullList.filter { it.name.contains(q, true) }
                }
            }
        }
    }
}
