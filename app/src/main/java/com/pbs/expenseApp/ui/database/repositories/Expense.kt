package com.pbs.expenseApp.ui.database.repositories

import com.pbs.expenseApp.ui.database.entities.Expense
import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {
    suspend fun insertExpense(expense: Expense)
    suspend fun updateExpense(expense: Expense)
    suspend fun deleteExpense(expense: Expense)
    fun getExpenseStream(uuid: String): Flow<Expense?>
    fun getAllExpensesStream(): Flow<List<Expense>>
}
