package com.pbs.expenseApp

import android.app.Application
import com.pbs.expenseApp.ui.database.AppContainer
import com.pbs.expenseApp.ui.database.AppDataContainer

class ExpenseApplication: Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}
