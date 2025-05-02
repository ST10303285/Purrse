package com.example.purrse.repo

import com.example.purrse.model.User
import com.example.purrse.data.CategoryDao
import com.example.purrse.model.Category
import kotlinx.coroutines.flow.Flow

class CategoryRepo (private val categoryDao: CategoryDao){

    suspend fun insertCategory(category: Category){
        categoryDao.insertCategory(category)
    }

    // <-- add this
    suspend fun getAllCategories(): List<Category> {
        return categoryDao.getAllCategories()
    }
    suspend fun getCategoriesForUser(userId: Int): List<Category> {
        return categoryDao.getCategoriesForUser(userId)
    }
    suspend fun deleteCategory(category: Category){
        categoryDao.deleteCategory(category)
    }

    suspend fun updateCategory(category: Category){
        categoryDao.updateCategory(category)
    }

}