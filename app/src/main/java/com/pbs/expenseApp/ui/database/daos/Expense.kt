package com.pbs.expenseApp.ui.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.pbs.expenseApp.ui.database.entities.Expense
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(expense: Expense)
    @Update
    suspend fun update(expense: Expense)
    @Delete
    suspend fun delete(expense: Expense)
    @Query("SELECT * from expenses WHERE uuid = :uuid")
    fun getExpense(uuid: String): Flow<Expense>
    @Query("SELECT * from expenses")
    fun getAllExpenses(): Flow<List<Expense>>
}
