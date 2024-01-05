package com.pbs.expenseApp.domain.repository

import com.pbs.expenseApp.domain.model.Category
import com.pbs.expenseApp.domain.model.CategoryType
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun insert(category: Category)
    suspend fun update(category: Category)
    suspend fun delete(category: Category)
    suspend fun insertAll(categories: List<Category>)
    suspend fun getCategory(uuid: String): Category
    fun getAllOrderByType(): Flow<List<Category>>
    fun getCategoryByCategoryType(categoryType: CategoryType): Flow<List<Category>>
}
