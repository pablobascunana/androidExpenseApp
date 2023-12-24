package com.pbs.expenseApp.data.repository

import com.pbs.expenseApp.data.daos.ExpenseDao
import com.pbs.expenseApp.domain.model.Expense
import com.pbs.expenseApp.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow

class ExpenseRepositoryImpl(private val expenseDao: ExpenseDao): ExpenseRepository {
    override suspend fun insertExpense(expense: Expense) = expenseDao.insert(expense)
    override suspend fun updateExpense(expense: Expense) = expenseDao.update(expense)
    override suspend fun deleteExpense(expense: Expense) = expenseDao.delete(expense)
    override fun getExpense(uuid: String): Flow<Expense?> = expenseDao.getExpense(uuid = "uuid")
    override fun getAllExpenses(): Flow<List<Expense>> = expenseDao.getAllExpenses()
}
