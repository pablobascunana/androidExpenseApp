package com.pbs.expenseApp.data.repository

import com.pbs.expenseApp.data.daos.CategoryDao
import com.pbs.expenseApp.domain.model.Category
import com.pbs.expenseApp.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow

class CategoryRepositoryImpl(private val categoryDao: CategoryDao): CategoryRepository {
    override suspend fun insertCategory(category: Category) = categoryDao.insert(category)
    override suspend fun insertAllCategories(categories: List<Category>) = categoryDao.insertAll(categories)
    override fun getAllCategoriesOrderByType(): Flow<List<Category>> = categoryDao.getAllCategoriesOrderByType()
}
