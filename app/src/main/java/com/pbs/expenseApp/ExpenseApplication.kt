package com.pbs.expenseApp

import android.app.Application
import com.pbs.expenseApp.database.AppContainer
import com.pbs.expenseApp.database.AppDataContainer

class ExpenseApplication: Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}
