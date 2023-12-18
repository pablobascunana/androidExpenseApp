package com.pbs.expenseApp.database

import android.content.Context
import com.pbs.expenseApp.database.repositories.CategoryRepository
import com.pbs.expenseApp.database.repositories.ExpenseRepository
import com.pbs.expenseApp.database.repositories.UserRepository
import com.pbs.expenseApp.database.repositories.offline.OfflineCategoryRepository
import com.pbs.expenseApp.database.repositories.offline.OfflineExpenseRepository
import com.pbs.expenseApp.database.repositories.offline.OfflineUserRepository

interface AppContainer {
    val userRepository: UserRepository
    val categoryRepository: CategoryRepository
    val expenseRepository: ExpenseRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val userRepository: UserRepository by lazy {
        OfflineUserRepository(ExpenseDatabase.getDatabase(context).userDao())
    }
    override val categoryRepository: CategoryRepository by lazy {
        OfflineCategoryRepository(ExpenseDatabase.getDatabase(context).categoryDao())
    }
    override val expenseRepository: ExpenseRepository by lazy {
        OfflineExpenseRepository(ExpenseDatabase.getDatabase(context).expenseDao())
    }
}
