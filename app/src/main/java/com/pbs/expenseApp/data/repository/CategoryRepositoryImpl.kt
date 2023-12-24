package com.pbs.expenseApp.data.repository

import com.pbs.expenseApp.data.daos.CategoryDao
import com.pbs.expenseApp.domain.model.Category
import com.pbs.expenseApp.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow

class CategoryRepositoryImpl(private val categoryDao: CategoryDao): CategoryRepository {
    override suspend fun insert(category: Category) = categoryDao.insert(category)
    override suspend fun insertAll(categories: List<Category>) = categoryDao.insertAll(categories)
    override suspend fun update(category: Category) = categoryDao.update(category)
    override fun getAllOrderByType(): Flow<List<Category>> = categoryDao.getAllOrderByType()
}
