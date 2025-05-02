package com.example.purrse.repo

import com.example.purrse.data.CategoryDao
import com.example.purrse.data.ExpenseDao
import com.example.purrse.data.GoalsDao
import com.example.purrse.model.Category
import com.example.purrse.model.Expense
import kotlinx.coroutines.flow.Flow
import com.example.purrse.data.CategorySpending

class ExpenseRepo(private val expenseDao: ExpenseDao) {

    suspend fun insertExpense(expense: Expense){
        expenseDao.insertExpense(expense)
    }

    suspend fun getExpenseForUserInRange(userId: Int, startDate: String, endDate: String):List<Expense>{
         return expenseDao.getExpensesForUserInRange(userId, startDate, endDate)
    }

    suspend fun getTotalPerCategory(userId: Int, startDate: String, endDate: String):List<CategorySpending>{
        return expenseDao.getTotalPerCategory(userId,startDate,endDate)
    }

    suspend fun getTotalSpending(): Double{
        return expenseDao.getTotalSpending()?:0.0
    }
    suspend fun deleteExpense(expense:Expense){
        expenseDao.deleteExpense(expense)
    }

}