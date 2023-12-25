package com.pbs.expenseApp.ui.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.pbs.expenseApp.R
import com.pbs.expenseApp.domain.model.CategoryType
import com.pbs.expenseApp.domain.model.Expense
import com.pbs.expenseApp.navigation.AppRoutes
import com.pbs.expenseApp.ui.AppViewModelProvider
import com.pbs.expenseApp.ui.components.AppColumn
import com.pbs.expenseApp.ui.components.AppList
import com.pbs.expenseApp.ui.components.AppText
import com.pbs.expenseApp.ui.viewmodels.ExpenseViewModel

@Composable
fun MyMonthlyExpenseList(
    navHostController: NavHostController
) {
    val expenseVM: ExpenseViewModel = viewModel(factory = AppViewModelProvider.Factory)

    val currentRoute = navHostController.currentBackStackEntry?.destination?.route

    var text = stringResource(id = R.string.add_monthly_expenses)
    var type = CategoryType.EXPENSE.value
    var listItems = expenseVM.expenseList
    if (currentRoute == AppRoutes.AddIncome.route) {
        text = stringResource(id = R.string.add_monthly_incomes)
        type = CategoryType.INCOME.value
        listItems = expenseVM.incomeList
    }

    AppColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_sm))
    ){
        AppText(
            text = text,
            fontSize = fontDimensionResource(id = R.dimen.font_md),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_sm))
        )
        if (listItems.isEmpty()) {
            AppText(
                text = stringResource(id = R.string.no_items_in_list),
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentHeight(align = Alignment.CenterVertically),
                textAlign = TextAlign.Center
            )
        } else {
            AppList(
                listItems,
                onEdit = {  },
                onDelete = {  }
            )
        }
    }
}
