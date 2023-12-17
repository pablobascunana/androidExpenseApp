package com.pbs.expenseApp.ui.database.repositories.offline

import com.pbs.expenseApp.ui.database.daos.ExpenseDao
import com.pbs.expenseApp.ui.database.entities.Expense
import com.pbs.expenseApp.ui.database.repositories.ExpenseRepository
import kotlinx.coroutines.flow.Flow

class OfflineExpenseRepository(private val expenseDao: ExpenseDao): ExpenseRepository {
    override suspend fun insertExpense(expense: Expense) = expenseDao.insert(expense)
    override suspend fun updateExpense(expense: Expense) = expenseDao.update(expense)
    override suspend fun deleteExpense(expense: Expense) = expenseDao.delete(expense)
    override fun getExpenseStream(uuid: String): Flow<Expense?> = expenseDao.getExpense(uuid = "uuid")
    override fun getAllExpensesStream(): Flow<List<Expense>> = expenseDao.getAllExpenses()
}
