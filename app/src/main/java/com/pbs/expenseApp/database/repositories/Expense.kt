package com.pbs.expenseApp.database.repositories

import androidx.lifecycle.LiveData
import com.pbs.expenseApp.database.entities.Expense

interface ExpenseRepository {
    suspend fun insertExpense(expense: Expense)
    suspend fun updateExpense(expense: Expense)
    suspend fun deleteExpense(expense: Expense)
    fun getExpenseStream(uuid: String): LiveData<Expense?>
    fun getAllExpensesStream(): LiveData<List<Expense>>
}
