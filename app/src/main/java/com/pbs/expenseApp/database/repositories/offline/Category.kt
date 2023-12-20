package com.pbs.expenseApp.database.repositories.offline

import androidx.lifecycle.LiveData
import com.pbs.expenseApp.database.daos.CategoryDao
import com.pbs.expenseApp.database.entities.Category
import com.pbs.expenseApp.database.repositories.CategoryRepository

class OfflineCategoryRepository(private val categoryDao: CategoryDao): CategoryRepository {
    override suspend fun insertCategory(category: Category) = categoryDao.insert(category)
    override suspend fun insertAllCategories(categories: List<Category>) = categoryDao.insertAll(categories)
    override suspend fun updateCategory(category: Category) = categoryDao.update(category)
    override suspend fun deleteCategory(category: Category) = categoryDao.delete(category)
    override fun getCategoryStream(uuid: String): LiveData<Category?> = categoryDao.getCategory(uuid = "uuid")
    override fun getAllCategoriesStream(): LiveData<List<Category>> = categoryDao.getAllCategories()
}
