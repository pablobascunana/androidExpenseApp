package com.pbs.expenseApp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pbs.expenseApp.R
import com.pbs.expenseApp.ui.AppViewModelProvider
import com.pbs.expenseApp.ui.components.AppCard
import com.pbs.expenseApp.ui.composables.MyText
import com.pbs.expenseApp.ui.screens.user.UserEntryViewModel
import kotlinx.coroutines.async

@Composable
fun AddExpense() {
    val userVm: UserEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val appVM: AppViewModel = viewModel(factory = AppViewModelProvider.Factory)
    var monthlySavings by remember { mutableIntStateOf(value = 0) }

    LaunchedEffect(key1 = true) {
        monthlySavings = async { userVm.getMonthlySavings(appVM.id) }.await()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        AppCard(monthlySavingsMessageCardContent(monthlySavings))
    }
}

@Composable
fun monthlySavingsMessageCardContent(monthlySavings: Int): @Composable () -> Unit {
    val value = @Composable {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 16.dp,
                    end = 16.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            MyText(id = R.string.add_expense_monthly_savings_1)
            Text(
                text = " $monthlySavings€ ",
                fontWeight = FontWeight.Bold
            )
            MyText(id = R.string.add_expense_monthly_savings_2)
        }
    }
    return value
}