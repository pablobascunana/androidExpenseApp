package com.pbs.expenseApp.ui.screens

import android.content.Context
import android.provider.Settings
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.pbs.expenseApp.R
import com.pbs.expenseApp.navigation.AppRoutes
import com.pbs.expenseApp.ui.AppViewModelProvider
import com.pbs.expenseApp.ui.screens.user.UserEntryViewModel
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(
    navHostController: NavHostController,
    context: Context
) {
    val coroutineScope = rememberCoroutineScope()
    val viewModel: UserEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)

    val id: String = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    LaunchedEffect(key1 = true) {
        coroutineScope.launch {
            viewModel.saveUser(id)
        }
        navHostController.popBackStack()
        navHostController.navigate(AppRoutes.BottomBar.route)
    }
    Splash()
}

@Composable
private fun Splash() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Savings,
            contentDescription = stringResource(R.string.splash_image),
            modifier = Modifier.size(
                dimensionResource(R.dimen.splash_width_height),
                dimensionResource(R.dimen.splash_width_height)
            ),
            tint = MaterialTheme.colorScheme.primary
        )
    }
}
