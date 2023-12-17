package com.pbs.expenseApp.ui.database.repositories.offline

import com.pbs.expenseApp.ui.database.daos.CategoryDao
import com.pbs.expenseApp.ui.database.entities.Category
import com.pbs.expenseApp.ui.database.repositories.CategoryRepository
import kotlinx.coroutines.flow.Flow

class OfflineCategoryRepository(private val categoryDao: CategoryDao): CategoryRepository {
    override suspend fun insertCategory(category: Category) = categoryDao.insert(category)
    override suspend fun insertAllCategories(categories: List<Category>) = categoryDao.insertAll(categories)
    override suspend fun updateCategory(category: Category) = categoryDao.update(category)
    override suspend fun deleteCategory(category: Category) = categoryDao.delete(category)
    override fun getCategoryStream(uuid: String): Flow<Category?> = categoryDao.getCategory(uuid = "uuid")
    override fun getAllCategoriesStream(): Flow<List<Category>> = categoryDao.getAllCategories()
}
