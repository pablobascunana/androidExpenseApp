package com.pbs.expenseApp.ui.database

import android.content.Context
import com.pbs.expenseApp.ui.database.repositories.CategoryRepository
// import com.pbs.expenseApp.ui.database.repositories.ExpenseRepository
import com.pbs.expenseApp.ui.database.repositories.UserRepository
import com.pbs.expenseApp.ui.database.repositories.offline.OfflineCategoryRepository
// import com.pbs.expenseApp.ui.database.repositories.offline.OfflineExpenseRepository
import com.pbs.expenseApp.ui.database.repositories.offline.OfflineUserRepository

interface AppContainer {
    val userRepository: UserRepository
    val categoryRepository: CategoryRepository
    /* val expenseRepository: ExpenseRepository */
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val userRepository: UserRepository by lazy {
        OfflineUserRepository(ExpenseDatabase.getDatabase(context).userDao())
    }
    override val categoryRepository: CategoryRepository by lazy {
        OfflineCategoryRepository(ExpenseDatabase.getDatabase(context).categoryDao())
    }
    /* override val expenseRepository: ExpenseRepository by lazy {
        OfflineExpenseRepository(ExpenseDatabase.getDatabase(context).expenseDao())
    } */
}
