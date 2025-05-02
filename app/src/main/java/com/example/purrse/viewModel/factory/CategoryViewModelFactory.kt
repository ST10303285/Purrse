package com.example.purrse.viewModel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.purrse.repo.CategoryRepo
import com.example.purrse.viewModel.CategoryViewModel

class CategoryViewModelFactory (
    private val repo: CategoryRepo,
    private val userId: Int
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
            return CategoryViewModel(repo, userId ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}