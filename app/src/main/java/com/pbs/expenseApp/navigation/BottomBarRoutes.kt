package com.pbs.expenseApp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector


enum class BottomBarRoutes(
    val id: Int,
    val title: String,
    val routes: String,
    val icon: ImageVector
) {
    ADD_EXPENSE(1, "Add expense", "/add_expense", Icons.Outlined.Add),
    RESUME(2, "Resume", "/resume", Icons.Outlined.BarChart),
    CONFIGURATION(3, "Configuration", "/configuration", Icons.Outlined.Settings)
}
