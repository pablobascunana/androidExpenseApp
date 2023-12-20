package com.pbs.expenseApp.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.pbs.expenseApp.navigation.AppRoutes
import com.pbs.expenseApp.ui.AppViewModelProvider
import com.pbs.expenseApp.ui.components.AppSplash
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
    AppSplash()
}
