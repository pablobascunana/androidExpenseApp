package com.pbs.expenseApp.navigation

sealed class AppRoutes(val route: String) {
    object Splash: AppRoutes(Routes.SPLASH.route)
    object BottomBar: AppRoutes(Routes.BOTTOM_BAR.route)
    object AddIncome: AppRoutes(Routes.ADD_MONTHLY_INCOMES.route)
    object AddExpense: AppRoutes(Routes.ADD_MONTHLY_EXPENSES.route)
}
