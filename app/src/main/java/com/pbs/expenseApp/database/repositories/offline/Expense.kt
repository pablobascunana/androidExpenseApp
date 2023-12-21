package com.pbs.expenseApp.database.repositories.offline

import androidx.lifecycle.LiveData
import com.pbs.expenseApp.database.daos.ExpenseDao
import com.pbs.expenseApp.database.entities.Expense
import com.pbs.expenseApp.database.repositories.ExpenseRepository

class OfflineExpenseRepository(private val expenseDao: ExpenseDao): ExpenseRepository {
    override suspend fun insertExpense(expense: Expense) = expenseDao.insert(expense)
    override suspend fun updateExpense(expense: Expense) = expenseDao.update(expense)
    override suspend fun deleteExpense(expense: Expense) = expenseDao.delete(expense)
    override fun getExpenseStream(uuid: String): LiveData<Expense?> = expenseDao.getExpense(uuid = "uuid")
    override fun getAllExpensesStream(): LiveData<List<Expense>> = expenseDao.getAllExpenses()
}
