package com.pbs.expenseApp.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.pbs.expenseApp.navigation.AppRoutes
import com.pbs.expenseApp.ui.AppViewModelProvider
import com.pbs.expenseApp.ui.components.AppSplash
import com.pbs.expenseApp.ui.viewmodels.CategoryViewModel
import com.pbs.expenseApp.ui.viewmodels.UserViewModel
import com.pbs.expenseApp.utils.AppUtils
import kotlinx.coroutines.async

@Composable
fun SplashScreen(navHostController: NavHostController) {
    val appId = AppUtils.getAppId(LocalContext.current)

    val userVM: UserViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val categoryVM: CategoryViewModel = viewModel(factory = AppViewModelProvider.Factory)

    LaunchedEffect(key1 = true) {
        async { userVM.userExists(appId) }.await()
        if (!userVM.userExists) {
            async { userVM.insertUser(appId) }.await()
            async { categoryVM.insertAll() }.await()
        }
        navHostController.popBackStack()
        navHostController.navigate(AppRoutes.BottomBar.route)
    }
    AppSplash()
}
