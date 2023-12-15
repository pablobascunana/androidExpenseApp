package com.pbs.expenseApp.navigation

sealed class AppRoutes(val route: String) {
    object Splash: AppRoutes("/splash")
    object BottomBar: AppRoutes("/bottom_bar")
}
