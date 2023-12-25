package com.pbs.expenseApp.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pbs.expenseApp.domain.model.Expense
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(expense: Expense)
    @Query("SELECT * from expenses")
    fun getAll(): Flow<List<Expense>>
}
