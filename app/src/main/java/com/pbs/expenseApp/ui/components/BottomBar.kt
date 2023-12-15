package com.pbs.expenseApp.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.pbs.expenseApp.navigation.BottomBarRoutes

@Composable
fun BottomBarRow(
    navHostController: NavHostController
) {

    val tabList = listOf(
        BottomBarRoutes.ADD_EXPENSE,
        BottomBarRoutes.RESUME,
        BottomBarRoutes.CONFIGURATION
    )

    val navStackBackEntry by navHostController.currentBackStackEntryAsState()
    val currentDestination = navStackBackEntry?.destination


    /* Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        tabList.forEach { tab ->
            BottomBarItems(
                tab = tab,
                currentDestination = currentDestination,
                navHostController = navHostController
            )
        }
    } */

}

@Composable
fun BottomBarItems(
    tab: BottomBarRoutes,
    currentDestination: NavDestination?,
    navHostController: NavHostController
) {

    val selected = currentDestination?.hierarchy?.any { it.route == tab.route } == true

    val contentColor =
        if (selected) Color.Unspecified else MaterialTheme.colorScheme.onPrimary

    IconButton(onClick = {
        navHostController.navigate(tab.route)
    }) {
        Icon(
            imageVector = tab.icon,
            contentDescription = "",
            tint = contentColor,
            modifier = Modifier.size(30.dp)
        )
    }
}
