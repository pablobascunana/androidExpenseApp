package com.pbs.expenseApp.domain.repository

import com.pbs.expenseApp.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun insertCategory(category: Category)
    suspend fun insertAllCategories(categories: List<Category>)
    fun getAllCategoriesOrderByType(): Flow<List<Category>>
}
