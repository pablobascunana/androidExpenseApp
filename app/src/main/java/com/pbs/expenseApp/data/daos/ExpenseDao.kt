package com.pbs.expenseApp.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pbs.expenseApp.domain.model.CategoryType
import com.pbs.expenseApp.domain.model.Expense
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(expense: Expense)
    @Query("SELECT * FROM expenses")
    fun getAll(): Flow<List<Expense>>
    @Query("SELECT * FROM expenses JOIN categories ON categories.uuid == expenses.categoryUuid " +
            "WHERE categories.type = :categoryType")
    fun getExpensesByCategoryType(categoryType: CategoryType): Flow<List<Expense>>
}
