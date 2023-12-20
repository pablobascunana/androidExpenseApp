package com.pbs.expenseApp.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.Observer
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
    val userVM: UserViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val categoryVM: CategoryEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val appVM: AppViewModel = viewModel(factory = AppViewModelProvider.Factory)

    val userExists = userVM.userExists.observeAsState()

    LaunchedEffect(key1 = true) {
        async { userVM.userExists(appVM.id) }.await()
        if (!userExists.value!!) {
            async { userVM.insertUser(appVM.id) }.await()
            async {
                categoryVM.saveCategories(categoryVM.defaultCategories, appVM.id)
            }.await()
        }
        navHostController.popBackStack()
        navHostController.navigate(AppRoutes.BottomBar.route)
    }
    AppSplash()
}
