package com.pbs.expenseApp.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pbs.expenseApp.domain.model.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(category: Category)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(entities: List<Category?>)
    @Query("SELECT * FROM categories ORDER BY type DESC")
    fun getAllCategoriesOrderByType(): Flow<List<Category>>
}
