package com.pbs.expenseApp.ui.components

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.pbs.expenseApp.appstate.rememberAppState
import com.pbs.expenseApp.navigation.AppNavigationGraph
import com.pbs.expenseApp.navigation.AppRoutes
import com.pbs.expenseApp.navigation.BottomBarRoutes
import com.pbs.expenseApp.ui.composables.MyAddCategoryFab
import com.pbs.expenseApp.ui.composables.MyAddExpenseFab
import com.pbs.expenseApp.ui.composables.MyAppNavigationBar

@Composable
fun AppScaffold() {
    val appState = rememberAppState()
    val currentRoute = appState.currentRoute
    Scaffold(
        floatingActionButton = {
            if (appState.shouldShowFloatingActionButton) {
                when (currentRoute) {
                    BottomBarRoutes.CONFIGURATION.route -> MyAddCategoryFab()
                    else -> MyAddExpenseFab()
                }
            }
        },
        bottomBar = {
            if (appState.shouldShowBottomBar) {
                MyAppNavigationBar(appState = appState)
            }
        }
    ) { innerPadding ->
        AppNavigationGraph(
            navHostController = appState.navHostController, padding = innerPadding
        )
    }
}
