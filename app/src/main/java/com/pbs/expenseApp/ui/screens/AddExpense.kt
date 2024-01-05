package com.pbs.expenseApp.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
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
import com.pbs.expenseApp.ui.viewmodels.HorizontalPagerViewModel
import com.pbs.expenseApp.ui.viewmodels.UserViewModel
import com.pbs.expenseApp.utils.AppUtils
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddExpense() {
    val context = LocalContext.current
    val userVM: UserViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val pagerVM: HorizontalPagerViewModel = viewModel(factory = AppViewModelProvider.Factory)

    LaunchedEffect(key1 = true) {
        userVM.viewModelScope.launch { userVM.getMonthlySavings(AppUtils.getAppId(context)) }
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
                MyMonthlySavingText(monthlySavings = userVM.monthlySavings)
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
            AppHorizontalPager(pageCount = 10) {
                AppText(
                    text = "Page: $it",
                    modifier = Modifier.fillMaxWidth().fillMaxHeight()
                )
            }
        }
    }
}
