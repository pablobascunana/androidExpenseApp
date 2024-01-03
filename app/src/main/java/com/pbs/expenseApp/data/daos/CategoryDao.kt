package com.pbs.expenseApp.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.pbs.expenseApp.domain.model.Category
import com.pbs.expenseApp.domain.model.CategoryType
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(category: Category)
    @Update
    suspend fun update(category: Category)
    @Delete
    suspend fun delete(category: Category)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(entities: List<Category?>)
    @Query("SELECT * FROM categories ORDER BY type DESC, isDefault")
    fun getAllOrderByType(): Flow<List<Category>>
    @Query("SELECT * FROM categories WHERE type = :categoryType")
    fun getCategoryByCategoryType(categoryType: CategoryType): Flow<List<Category>>
}
