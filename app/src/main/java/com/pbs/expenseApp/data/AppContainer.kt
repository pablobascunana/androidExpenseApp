package com.pbs.expenseApp.data

import android.content.Context
import com.pbs.expenseApp.data.repository.CategoryRepositoryImpl
import com.pbs.expenseApp.data.repository.ExpenseRepositoryImpl
import com.pbs.expenseApp.data.repository.UserRepositoryImpl
import com.pbs.expenseApp.domain.repository.CategoryRepository
import com.pbs.expenseApp.domain.repository.ExpenseRepository
import com.pbs.expenseApp.domain.repository.UserRepository

interface AppContainer {
    val userRepository: UserRepository
    val categoryRepository: CategoryRepository
    val expenseRepository: ExpenseRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val userRepository: UserRepository by lazy {
        UserRepositoryImpl(ExpenseDatabase.getDatabase(context).userDao())
    }
    override val categoryRepository: CategoryRepository by lazy {
        CategoryRepositoryImpl(ExpenseDatabase.getDatabase(context).categoryDao())
    }
    override val expenseRepository: ExpenseRepository by lazy {
        ExpenseRepositoryImpl(ExpenseDatabase.getDatabase(context).expenseDao())
    }
}
