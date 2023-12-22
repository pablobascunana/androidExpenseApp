package com.pbs.expenseApp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.pbs.expenseApp.appstate.rememberAppState
import com.pbs.expenseApp.navigation.AppNavigationGraph

@Composable
fun AppScaffold() {
    val appState = rememberAppState()
    Scaffold(
        floatingActionButton = {
            if (appState.shouldShowFloatingActionButton) {
                FloatingActionButton(
                    onClick = {  },
                ) {
                    Icon(Icons.Filled.Add, "Floating action button.")
                }
            }
        },
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
