package com.pbs.expenseApp.ui.components

import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.pbs.expenseApp.states.rememberAppState
import com.pbs.expenseApp.navigation.AppNavigationGraph
import com.pbs.expenseApp.states.rememberSnackBarState
import com.pbs.expenseApp.ui.composables.MyAddCategoryFab
import com.pbs.expenseApp.ui.composables.MyAppNavigationBar

@Composable
fun AppScaffold() {
    val appState = rememberAppState()
    val snackBarHostState = rememberSnackBarState()

    Scaffold(
        floatingActionButton = {
            if (appState.shouldShowFloatingActionButton) {
                MyAddCategoryFab()
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState.state)
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
