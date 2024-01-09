package com.pbs.expenseApp.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.pbs.expenseApp.domain.model.CategoryType
import com.pbs.expenseApp.domain.model.Expense
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(expense: Expense)
    @Query("SELECT * FROM expenses")
    fun getAll(): Flow<List<Expense>>
    @Query("SELECT expenses.uuid, expenses.userUuid, categoryUuid, amount, creationDate, payMethod, description, categories.name " +
            "FROM expenses " +
            "JOIN categories ON categories.uuid == expenses.categoryUuid " +
            "WHERE categories.type = :categoryType")
    fun getExpensesByCategoryType(categoryType: CategoryType): Flow<List<Expense>>
    @Query("SELECT expenses.uuid, expenses.userUuid, categoryUuid, amount, creationDate, payMethod, description, categories.name, categories.type " +
            "FROM expenses " +
            "JOIN categories ON categories.uuid == expenses.categoryUuid " +
            "WHERE expenses.creationDate IS NULL")
    fun getMonthlyExpenses(): Flow<List<Expense>>
    @Update
    suspend fun update(expense: Expense)
    @Delete
    suspend fun delete(expense: Expense)
}
