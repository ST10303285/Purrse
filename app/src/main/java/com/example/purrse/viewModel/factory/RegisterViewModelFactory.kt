package com.example.purrse.viewModel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.purrse.data.CategoryDao
import com.example.purrse.repo.UserRepo
import com.example.purrse.viewModel.RegisterViewModel

class RegisterViewModelFactory(private val userRepo: UserRepo, private val categoryDao: CategoryDao) : ViewModelProvider.Factory  {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(userRepo,categoryDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}