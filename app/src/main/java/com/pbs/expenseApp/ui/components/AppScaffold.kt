package com.pbs.expenseApp.ui.components

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.pbs.expenseApp.appstate.rememberAppState
import com.pbs.expenseApp.navigation.AppNavigationGraph

@Composable
fun AppScaffold() {
    val appState = rememberAppState()
    Scaffold(
        bottomBar = {
            if (appState.shouldShowBottomBar) {
                AppNavigationBar(appState = appState)
            }
        }
    ) { innerPadding ->
        AppNavigationGraph(
            navController = appState.navHostController,
            padding = innerPadding
        )
    }
}
