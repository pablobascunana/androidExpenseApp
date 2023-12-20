package com.pbs.expenseApp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Savings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.pbs.expenseApp.R
import com.pbs.expenseApp.navigation.AppRoutes
import com.pbs.expenseApp.ui.AppViewModelProvider
import com.pbs.expenseApp.ui.components.AppColumn
import com.pbs.expenseApp.ui.components.AppIcon
import com.pbs.expenseApp.ui.screens.category.CategoryEntryViewModel
import com.pbs.expenseApp.ui.viewmodels.UserViewModel
import kotlinx.coroutines.async

@Composable
fun SplashScreen(navHostController: NavHostController) {
    val userVm: UserViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val categoryVm: CategoryEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val appVM: AppViewModel = viewModel(factory = AppViewModelProvider.Factory)

    LaunchedEffect(key1 = true) {
        val exist = async { userVm.userExists(appVM.id) }.await()
        if (!exist) {
            async { userVm.insertUser(appVM.id) }.await()
            async {
                categoryVm.saveCategories(categoryVm.defaultCategories, appVM.id)
            }.await()
        }
        navHostController.popBackStack()
        navHostController.navigate(AppRoutes.BottomBar.route)
    }
    Splash()
}

@Composable
private fun Splash() {
    // TODO REFACTOR: use MyColumn and MyIcon
    AppColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AppIcon(
            imageVector = Icons.Default.Savings,
            contentDescription = stringResource(R.string.splash_image),
            modifier = Modifier.size(
                dimensionResource(R.dimen.splash_width_height),
                dimensionResource(R.dimen.splash_width_height)
            )
        )
    }
}
