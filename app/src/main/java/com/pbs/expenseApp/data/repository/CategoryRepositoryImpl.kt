package com.pbs.expenseApp.data.repository

import androidx.room.Query
import com.pbs.expenseApp.data.daos.CategoryDao
import com.pbs.expenseApp.domain.model.Category
import com.pbs.expenseApp.domain.model.CategoryType
import com.pbs.expenseApp.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow

class CategoryRepositoryImpl(private val categoryDao: CategoryDao): CategoryRepository {
    override suspend fun insert(category: Category) = categoryDao.insert(category)
    override suspend fun insertAll(categories: List<Category>) = categoryDao.insertAll(categories)
    override suspend fun update(category: Category) = categoryDao.update(category)
    override suspend fun delete(category: Category) = categoryDao.delete(category)
    override fun getAllOrderByType(): Flow<List<Category>> = categoryDao.getAllOrderByType()
    override fun getCategoryByCategoryType(categoryType: CategoryType): Flow<List<Category>> =
        categoryDao.getCategoryByCategoryType(categoryType)
}
