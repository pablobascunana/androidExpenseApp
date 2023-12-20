package com.pbs.expenseApp.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pbs.expenseApp.R
import com.pbs.expenseApp.ui.AppViewModelProvider
import com.pbs.expenseApp.ui.components.AppCard
import com.pbs.expenseApp.ui.components.AppColumn
import com.pbs.expenseApp.ui.components.AppRow
import com.pbs.expenseApp.ui.composables.MyMonthlySavingText
import com.pbs.expenseApp.ui.viewmodels.UserViewModel
import kotlinx.coroutines.async

@Composable
fun AddExpense() {
    val userVM: UserViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val appVM: AppViewModel = viewModel(factory = AppViewModelProvider.Factory)

    LaunchedEffect(key1 = true) {
        async { userVM.getMonthlySavings(appVM.id) }.await()
    }

    AppColumn(
        modifier = Modifier
        .fillMaxSize()
        .padding(dimensionResource(id = R.dimen.padding_sm))
    ) {
        AppCard {
            AppRow(modifier = Modifier
                .fillMaxSize()
                .padding(start = dimensionResource(id = R.dimen.padding_sm),  end = dimensionResource(id = R.dimen.padding_sm))
            ) {
                MyMonthlySavingText(monthlySavings = userVM.monthlySavings.value ?: 0)
            }
        }
    }
}
