package com.example.purrse.data

import androidx.room.*
import com.example.purrse.model.Expense


@Dao
interface ExpenseDao {

    @Insert(onConflict=OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense : Expense):Long

    @Query("SELECT *FROM expenses WHERE userId= :userId AND date BETWEEN :startDate AND :endDate")
    suspend fun getExpensesForUserInRange(userId: Int, startDate: String, endDate: String): List<Expense>

    @Query("""SELECT categoryId, SUM(amount) AS total 
            FROM expenses 
            WHERE userId =:userId 
            AND date BETWEEN :startDate AND :endDate GROUP BY categoryId""")
    suspend fun getTotalPerCategory(userId: Int, startDate: String, endDate: String): List<CategorySpending>

    @Delete
    suspend fun deleteExpense(expense: Expense)

}
data class CategorySpending(
    val categoryId : Int,
    val total: Double
)

