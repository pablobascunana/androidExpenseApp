package com.pbs.expenseApp.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.pbs.expenseApp.database.entities.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(category: Category)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(entities: List<Category?>)
    @Update
    suspend fun update(category: Category)
    @Delete
    suspend fun delete(category: Category)
    @Query("SELECT * from categories WHERE uuid = :uuid")
    fun getCategory(uuid: String): Flow<Category>
    @Query("SELECT * from categories")
    fun getAllCategories(): Flow<List<Category>>
}
