package com.pbs.expenseApp.ui.components

import android.content.Context
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.pbs.expenseApp.appstate.rememberAppState
import com.pbs.expenseApp.navigation.AppNavigationGraph

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(context: Context) {
    val appState = rememberAppState()
    Scaffold(
        bottomBar = {
            if (appState.shouldShowBottomBar) {
                BottomBarNavigation(appState = appState)
            }
        }
    ) { innerPadding ->
        AppNavigationGraph(
            navController = appState.navHostController,
            context = context,
            padding = innerPadding
        )
    }
}
