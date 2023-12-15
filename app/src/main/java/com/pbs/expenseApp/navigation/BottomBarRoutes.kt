package com.pbs.expenseApp.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.pbs.expenseApp.R


enum class BottomBarRoutes(
    val id: Int,
    @StringRes val title: Int,
    val route: String,
    val icon: ImageVector
) {
    ADD_EXPENSE(
        id = 1,
        title = R.string.splash_image,
        route = Routes.ADD_EXPENSE.route,
        icon = Icons.Outlined.Add
    ),
    RESUME(
        id = 2,
        title = R.string.resume,
        route = Routes.RESUME.route,
        icon = Icons.Outlined.BarChart
    ),
    CONFIGURATION(
        id = 3,
        title = R.string.configuration,
        route = Routes.CONFIGURATION.route,
        icon = Icons.Outlined.Settings
    )
}
