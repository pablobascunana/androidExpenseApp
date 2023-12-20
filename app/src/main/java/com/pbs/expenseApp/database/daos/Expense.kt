package com.pbs.expenseApp.database.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.pbs.expenseApp.database.entities.Expense

@Dao
interface ExpenseDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(expense: Expense)
    @Update
    suspend fun update(expense: Expense)
    @Delete
    suspend fun delete(expense: Expense)
    @Query("SELECT * from expenses WHERE uuid = :uuid")
    fun getExpense(uuid: String): LiveData<Expense?>
    @Query("SELECT * from expenses")
    fun getAllExpenses(): LiveData<List<Expense>>
}
