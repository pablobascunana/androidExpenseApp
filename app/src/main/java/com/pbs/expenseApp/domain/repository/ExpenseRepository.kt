package com.pbs.expenseApp.domain.repository

import com.pbs.expenseApp.domain.model.Expense
import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {
    suspend fun insert(expense: Expense)
    fun getAll(): Flow<List<Expense>>
}
