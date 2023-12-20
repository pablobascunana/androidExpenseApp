package com.pbs.expenseApp.database.repositories

import androidx.lifecycle.LiveData
import com.pbs.expenseApp.database.entities.Category

interface CategoryRepository {
    suspend fun insertCategory(category: Category)
    suspend fun insertAllCategories(categories: List<Category>)
    suspend fun updateCategory(category: Category)
    suspend fun deleteCategory(category: Category)
    fun getCategoryStream(uuid: String): LiveData<Category?>
    fun getAllCategoriesStream(): LiveData<List<Category>>
}
