package com.pbs.expenseApp.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pbs.expenseApp.R
import com.pbs.expenseApp.ui.AppViewModelProvider
import com.pbs.expenseApp.ui.components.AppCard
import com.pbs.expenseApp.ui.components.AppColumn
import com.pbs.expenseApp.ui.components.AppHorizontalPager
import com.pbs.expenseApp.ui.components.AppRow
import com.pbs.expenseApp.ui.components.AppText
import com.pbs.expenseApp.ui.composables.MyMonthlySavingText
import com.pbs.expenseApp.ui.viewmodels.ExpenseViewModel
import com.pbs.expenseApp.ui.viewmodels.HorizontalPagerViewModel
import com.pbs.expenseApp.ui.viewmodels.UserViewModel
import com.pbs.expenseApp.utils.AppUtils
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AddExpense() {
    val context = LocalContext.current
    val userVM: UserViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val pagerVM: HorizontalPagerViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val expenseVM: ExpenseViewModel = viewModel(factory = AppViewModelProvider.Factory)

    pagerVM.calculatePageCount(userVM.user.creationDate)

    LaunchedEffect(key1 = true) {
        userVM.viewModelScope.launch { userVM.getUser(AppUtils.getAppId(context)) }
        expenseVM.viewModelScope.launch { expenseVM.getMonthlyExpenses() }
    }

    AppColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.padding_sm))
    ) {
        AppCard(modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(R.dimen.card_height_sm))
        ) {
            AppRow(modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = dimensionResource(id = R.dimen.padding_sm),
                    end = dimensionResource(id = R.dimen.padding_sm)
                )
            ) {
                MyMonthlySavingText(monthlySavings = userVM.user.monthlySavings)
            }
        }

        AppRow(modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(R.dimen.card_height_sm))
        ) {
            AppText(text = pagerVM.formattedPagerDate)
        }

        AppColumn(modifier = Modifier
            .fillMaxSize()
        ) {
            AppHorizontalPager(pageCount = pagerVM.pageCount) {
                AppColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (expenseVM.monthlyExpenses.isEmpty()) {
                        AppText(
                            text = stringResource(id = R.string.expense_empty),
                        )
                    } else {
                        AppText(
                            text = "Pues tendremos que mostrar un gráfico, no?",
                        )
                    }
                }
            }
        }
    }
}
