package com.pbs.expenseApp.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import com.pbs.expenseApp.appstate.AppState
import com.pbs.expenseApp.navigation.BottomBarRoutes

@Composable
fun BottomBarNavigation(appState: AppState) {
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
                    Text(
                        text = title,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                },
                icon = {
                    Icon(
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