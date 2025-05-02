package com.example.purrse.data

import androidx.room.*
import com.example.purrse.model.Category
import com.example.purrse.model.Goal
import com.example.purrse.model.User

@Dao
interface CategoryDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: Category):Long

    @Query("SELECT * FROM categories WHERE userId = :userId")
    suspend fun  getCategoriesForUser(userId: Int): List<Category>

    @Query("SELECT * FROM categories")
    suspend fun getAllCategories(): List<Category>

   @Delete
    suspend fun deleteCategory(category: Category)

    @Update
    suspend fun  updateCategory(category: Category)

}