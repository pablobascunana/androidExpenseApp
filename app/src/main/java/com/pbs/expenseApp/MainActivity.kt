package com.pbs.expenseApp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import com.pbs.expenseApp.appstate.rememberAppState
import com.pbs.expenseApp.navigation.AppNavigation
import com.pbs.expenseApp.navigation.BottomBarRoutes
import com.pbs.expenseApp.ui.theme.ExpenseAppTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val appState = rememberAppState()
            ExpenseAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        bottomBar = {
                            if (appState.shouldShowBottomBar) {
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
                                        NavigationBarItem(
                                            selected = item.route == currentRoute,
                                            label = {
                                                Text(
                                                    text = stringResource(item.title),
                                                    color = Color.Black
                                                )
                                            },
                                            icon = {
                                                Icon(
                                                    imageVector = item.icon,
                                                    contentDescription = ""
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
                                /* BottomAppBar(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    contentPadding = PaddingValues(horizontal = 20.dp),
                                    modifier = Modifier
                                        .height(70.dp)
                                        /* .clip(
                                            RoundedCornerShape(
                                                topStart = 24.dp, topEnd = 24.dp
                                            )
                                        ) */
                                ) {
                                    BottomBarRow(
                                        navHostController = appState.navHostController,
                                    )
                                } */
                            }
                        }
                    ) { innerPadding ->
                        AppNavigation(
                            navController = appState.navHostController,
                            padding = innerPadding
                        )
                    }
                }
            }
        }
    }
}
