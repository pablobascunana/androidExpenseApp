package com.pbs.expenseApp.ui.composables.addmonthlymovements

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pbs.expenseApp.R
import com.pbs.expenseApp.ui.AppViewModelProvider
import com.pbs.expenseApp.ui.components.AppIcon
import com.pbs.expenseApp.ui.components.AppModalBottomSheet
import com.pbs.expenseApp.ui.components.AppRow
import com.pbs.expenseApp.ui.components.AppText
import com.pbs.expenseApp.ui.viewmodels.ExpenseViewModel

@Composable
fun MyAddExpenseFab() {
    val expenseVM: ExpenseViewModel = viewModel(factory = AppViewModelProvider.Factory)

    FloatingActionButton(
        onClick = {
            expenseVM.addExpense = !expenseVM.addExpense
        },
    ) {
        AppRow(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_xs))
        ) {
            AppIcon(
                imageVector = Icons.Filled.Add,
                contentDescription = stringResource(id = R.string.configuration_add_category)
            )
            AppText(
                text = stringResource(id = R.string.add)
            )
        }
    }
    if(expenseVM.addExpense) {
        AppModalBottomSheet(onDismissRequest = {
            expenseVM.addExpense = !expenseVM.addExpense
        }
        ) {
            MyAddExpenseModalBottomSheet(
                onClickNegative = {
                    expenseVM.addExpense = !expenseVM.addExpense
                },
                onClickPositive = {  }
            )
        }
    }
}