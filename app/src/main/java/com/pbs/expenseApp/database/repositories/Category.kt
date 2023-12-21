package com.pbs.expenseApp.database.repositories

import androidx.lifecycle.MutableLiveData
import com.pbs.expenseApp.database.entities.Category
interface CategoryRepository {
    suspend fun insertCategory(category: Category)
    suspend fun insertAllCategories(categories: List<Category>)
    suspend fun updateCategory(category: Category)
    suspend fun deleteCategory(category: Category)
    suspend fun getCategoryStream(uuid: String): Category?
    suspend fun getAllCategoriesStream(): List<Category>
}
