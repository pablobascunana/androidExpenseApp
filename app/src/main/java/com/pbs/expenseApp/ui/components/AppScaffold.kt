package com.pbs.expenseApp.ui.components

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.pbs.expenseApp.appstate.rememberAppState
import com.pbs.expenseApp.navigation.AppNavigationGraph
import com.pbs.expenseApp.ui.composables.MyAddCategoryFab
import com.pbs.expenseApp.ui.composables.MyAppNavigationBar

@Composable
fun AppScaffold() {
    val appState = rememberAppState()
    Scaffold(
        floatingActionButton = {
            if (appState.shouldShowFloatingActionButton) {
                MyAddCategoryFab()
            }
        },
        bottomBar = {
            if (appState.shouldShowBottomBar) {
                MyAppNavigationBar(appState = appState)
            }
        }
    ) { innerPadding ->
        AppNavigationGraph(
            navController = appState.navHostController,
            padding = innerPadding
        )
    }
}
