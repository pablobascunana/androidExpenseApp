package com.pbs.expenseApp.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import com.pbs.expenseApp.appstate.AppState
import com.pbs.expenseApp.appstate.rememberAppState
import com.pbs.expenseApp.navigation.BottomBarRoutes

@Composable
fun BottomBarNavigation(appState: AppState) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.secondary,
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
                        color = Color.Black
                    )
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = "$title icon"
                    )
                },
                onClick = {
                    appState.navHostController.navigate(item.route)
                },
                colors = NavigationBarItemDefaults.colors(
                    unselectedTextColor = MaterialTheme.colorScheme.secondary,
                    selectedTextColor = MaterialTheme.colorScheme.tertiary
                ),
            )
        }
    }
}
