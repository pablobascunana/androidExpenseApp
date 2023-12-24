package com.pbs.expenseApp.domain.repository

import com.pbs.expenseApp.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun insert(category: Category)
    suspend fun update(category: Category)
    suspend fun insertAll(categories: List<Category>)
    fun getAllOrderByType(): Flow<List<Category>>
}
