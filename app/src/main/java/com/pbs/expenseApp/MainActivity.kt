package com.pbs.expenseApp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.pbs.expenseApp.ui.components.AppSurface
import com.pbs.expenseApp.ui.theme.ExpenseAppTheme

// TODO REFACTOR: remove offline repository
// TODO REFACTOR: pass items to AppNavigationBar
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExpenseAppTheme(dynamicColor = false) {
                AppSurface()
            }
        }
    }
}
