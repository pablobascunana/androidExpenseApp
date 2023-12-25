package com.pbs.expenseApp.appstate

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.compose.runtime.Stable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.pbs.expenseApp.navigation.AppRoutes
import com.pbs.expenseApp.navigation.BottomBarRoutes
import com.pbs.expenseApp.navigation.Routes

@Composable
fun rememberAppState(
    navHostController: NavHostController = rememberNavController()
) = remember(navHostController) {
    AppState(navHostController)
}

@Stable
class AppState(
    val navHostController: NavHostController
) {

    private val routes = BottomBarRoutes.values().map { it.route }

    val shouldShowBottomBar: Boolean
        @Composable get() = currentRoute in routes

    val shouldShowFloatingActionButton: Boolean
    @Composable get() = currentRoute in listOf(
        Routes.CONFIGURATION.route,
        AppRoutes.AddIncome.route,
        AppRoutes.AddExpense.route
    )

    val currentRoute: String
        @Composable get() =
            navHostController.currentBackStackEntryAsState().value?.destination?.route ?: ""
}
