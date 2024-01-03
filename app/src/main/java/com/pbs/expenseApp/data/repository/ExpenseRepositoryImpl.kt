package com.pbs.expenseApp.data.repository

import com.pbs.expenseApp.data.daos.ExpenseDao
import com.pbs.expenseApp.domain.model.CategoryType
import com.pbs.expenseApp.domain.model.Expense
import com.pbs.expenseApp.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow

class ExpenseRepositoryImpl(private val expenseDao: ExpenseDao): ExpenseRepository {
    override suspend fun insert(expense: Expense) = expenseDao.insert(expense)
    override fun getAll(): Flow<List<Expense>> = expenseDao.getAll()
    override suspend fun getExpensesByCategoryType(categoryType: CategoryType): Flow<List<Expense>> =
        expenseDao.getExpensesByCategoryType(categoryType)
}
