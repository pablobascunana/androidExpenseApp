package com.pbs.expenseApp.ui.composables

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import com.pbs.expenseApp.appstate.AppState
import com.pbs.expenseApp.navigation.BottomBarRoutes
import com.pbs.expenseApp.ui.components.AppIcon
import com.pbs.expenseApp.ui.components.AppText

@Composable
fun MyAppNavigationBar(appState: AppState) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
    ) {
        val navigationBarItems = listOf(
            BottomBarRoutes.ADD_EXPENSE,
            BottomBarRoutes.RESUME,
            BottomBarRoutes.CONFIGURATION
        )
        val navStackBackEntry by appState.navHostController.currentBackStackEntryAsState()
        val currentRoute = navStackBackEntry?.destination?.route

        navigationBarItems.forEach { item ->
            val title = stringResource(item.title)
            NavigationBarItem(
                selected = item.route == currentRoute,
                label = {
                    AppText(
                        text = stringResource(id = item.title),
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                },
                icon = {
                    AppIcon(
                        imageVector = item.icon,
                        contentDescription = "$title icon",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                },
                onClick = {
                    appState.navHostController.navigate(item.route)
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.secondaryContainer,
                ),
            )
        }
    }
}
