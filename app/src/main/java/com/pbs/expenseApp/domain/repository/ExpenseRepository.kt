package com.pbs.expenseApp.domain.repository

import com.pbs.expenseApp.domain.model.CategoryType
import com.pbs.expenseApp.domain.model.Expense
import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {
    suspend fun insert(expense: Expense)
    fun getAll(): Flow<List<Expense>>
    suspend fun getExpensesByCategoryType(categoryType: CategoryType): Flow<List<Expense>>
    suspend fun update(expense: Expense)
    suspend fun delete(expense: Expense)
}
