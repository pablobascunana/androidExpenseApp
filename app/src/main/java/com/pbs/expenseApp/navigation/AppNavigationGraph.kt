package com.pbs.expenseApp.navigation

import android.content.Context
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.pbs.expenseApp.ui.screens.AddExpense
import com.pbs.expenseApp.ui.screens.Configuration
import com.pbs.expenseApp.ui.screens.Resume
import com.pbs.expenseApp.ui.screens.SplashScreen

@Composable
fun AppNavigationGraph(
    navController: NavHostController,
    context: Context,
    padding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = AppRoutes.Splash.route,
        modifier = Modifier.padding(padding)
    ) {
        composable(AppRoutes.Splash.route) {
            SplashScreen(navController, context)
        }
        navigation(
            route = AppRoutes.BottomBar.route,
            startDestination = BottomBarRoutes.ADD_EXPENSE.route
        ) {
            composable(BottomBarRoutes.ADD_EXPENSE.route) {
                AddExpense()
            }
            composable(BottomBarRoutes.RESUME.route) {
                Resume()
            }
            composable(BottomBarRoutes.CONFIGURATION.route) {
                Configuration()
            }
        }
    }
}