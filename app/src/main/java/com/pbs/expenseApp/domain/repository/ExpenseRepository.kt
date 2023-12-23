package com.pbs.expenseApp.domain.repository

import com.pbs.expenseApp.domain.model.Expense
import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {
    suspend fun insertExpense(expense: Expense)
    suspend fun updateExpense(expense: Expense)
    suspend fun deleteExpense(expense: Expense)
    fun getExpense(uuid: String): Flow<Expense?>
    fun getAllExpenses(): Flow<List<Expense>>
}
