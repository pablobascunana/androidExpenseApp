package com.pbs.expenseApp.ui.database.repositories

import com.pbs.expenseApp.ui.database.entities.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun insertCategory(category: Category)
    suspend fun insertAllCategories(categories: List<Category>)
    suspend fun updateCategory(category: Category)
    suspend fun deleteCategory(category: Category)
    fun getCategoryStream(uuid: String): Flow<Category?>
    fun getAllCategoriesStream(): Flow<List<Category>>
}
