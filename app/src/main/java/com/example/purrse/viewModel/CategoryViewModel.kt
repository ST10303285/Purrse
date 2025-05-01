package com.example.purrse.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.purrse.model.Category
import com.example.purrse.repo.CategoryRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CategoryViewModel(private val categoryRepo: CategoryRepo): ViewModel() {

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val  categories: StateFlow<List<Category>> = _categories

    fun loadCategories(userId: Int){
        viewModelScope.launch {
            categoryRepo.getCategoriesForUser(userId)
        }
    }

    fun addCategory(category: Category){
        viewModelScope.launch {
            categoryRepo.insertCategory(category)
            loadCategories(category.userId)
        }
    }
}