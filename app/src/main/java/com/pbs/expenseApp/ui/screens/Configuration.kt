package com.pbs.expenseApp.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pbs.expenseApp.R
import com.pbs.expenseApp.ui.AppViewModelProvider
import com.pbs.expenseApp.ui.components.AppCard
import com.pbs.expenseApp.ui.components.AppColumn
import com.pbs.expenseApp.ui.components.AppIcon
import com.pbs.expenseApp.ui.components.AppModalBottomSheet
import com.pbs.expenseApp.ui.components.AppRow
import com.pbs.expenseApp.ui.composables.MyAddIncomesAndExpensesGrid
import com.pbs.expenseApp.ui.composables.MyCategoryList
import com.pbs.expenseApp.ui.composables.MyModalMonthlySavingModalBottomSheet
import com.pbs.expenseApp.ui.composables.MyMonthlySavingText
import com.pbs.expenseApp.ui.viewmodels.CardItem
import com.pbs.expenseApp.ui.viewmodels.CategoryViewModel
import com.pbs.expenseApp.ui.viewmodels.ConfigurationViewModel
import com.pbs.expenseApp.ui.viewmodels.UserViewModel
import com.pbs.expenseApp.utils.AppUtils
import kotlinx.coroutines.async

@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun Configuration() {
    val context = LocalContext.current

    val userVM: UserViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val configurationVM: ConfigurationViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val categoriesVM: CategoryViewModel = viewModel(factory = AppViewModelProvider.Factory)

    LaunchedEffect(key1 = true) {
        async { userVM.getMonthlySavings(AppUtils.getAppId(context)) }.await()
    }

    configurationVM.cardItems.forEach { item ->
        GetCardColors(item)
    }

    AppColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = dimensionResource(id = R.dimen.padding_sm),
                start = dimensionResource(id = R.dimen.padding_sm),
                end = dimensionResource(id = R.dimen.padding_sm),
            )
    ) {
        AppCard(modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(R.dimen.card_height))
        ) {
            AppRow(modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = dimensionResource(id = R.dimen.padding_sm),
                    end = dimensionResource(id = R.dimen.padding_xs)
                )
            ) {
                MyMonthlySavingText(monthlySavings = userVM.monthlySavings)
                Spacer(modifier = Modifier.weight(1f))
                if (!configurationVM.editMonthlySavings) {
                    AppIcon(
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = "Edit icon",
                        modifier = Modifier.clickable {
                            configurationVM.editMonthlySavings = !configurationVM.editMonthlySavings
                        }
                    )
                }
            }
        }
        MyAddIncomesAndExpensesGrid(configurationVM.cardItems)
        MyCategoryList(categoriesVM.categories)
        if (configurationVM.editMonthlySavings) {
            AppModalBottomSheet(onDismissRequest = {
                configurationVM.editMonthlySavings = !configurationVM.editMonthlySavings
            }) {
                MyModalMonthlySavingModalBottomSheet()
            }
        }
    }
}

@Composable
private fun GetCardColors(item: CardItem) {
    when (item.title) {
        R.string.configuration_card_savings ->
            item.containerColor = MaterialTheme.colorScheme.tertiaryContainer
        R.string.configuration_card_expenses ->
            item.containerColor = MaterialTheme.colorScheme.errorContainer
        else -> item.containerColor = MaterialTheme.colorScheme.secondaryContainer
    }
}
