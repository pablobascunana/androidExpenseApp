package com.pbs.expenseApp.ui.screens

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
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.pbs.expenseApp.R
import com.pbs.expenseApp.ui.AppViewModelProvider
import com.pbs.expenseApp.ui.components.AppCard
import com.pbs.expenseApp.ui.components.AppColumn
import com.pbs.expenseApp.ui.components.AppIcon
import com.pbs.expenseApp.ui.components.AppModalBottomSheet
import com.pbs.expenseApp.ui.components.AppRow
import com.pbs.expenseApp.ui.composables.addmonthlymovements.MyAddIncomesAndExpensesGrid
import com.pbs.expenseApp.ui.composables.addcategories.MyCategoryList
import com.pbs.expenseApp.ui.composables.MyModalMonthlySavingModalBottomSheet
import com.pbs.expenseApp.ui.composables.MyMonthlySavingText
import com.pbs.expenseApp.ui.viewmodels.CardItem
import com.pbs.expenseApp.ui.viewmodels.CategoryViewModel
import com.pbs.expenseApp.ui.viewmodels.ConfigurationViewModel
import com.pbs.expenseApp.ui.viewmodels.ExposedDropDownViewModel
import com.pbs.expenseApp.ui.viewmodels.UserViewModel
import com.pbs.expenseApp.utils.AppUtils
import kotlinx.coroutines.launch

@Composable
fun Configuration(
    navHostController: NavHostController
) {
    val context = LocalContext.current

    val userVM: UserViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val configurationVM: ConfigurationViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val categoriesVM: CategoryViewModel = viewModel(factory = AppViewModelProvider.Factory)

    LaunchedEffect(key1 = true) {
        userVM.viewModelScope.launch { userVM.getMonthlySavings(AppUtils.getAppId(context)) }
    }

    configurationVM.cardItems.forEach { item -> GetCardColors(item) }

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
            .height(dimensionResource(R.dimen.card_height_sm))
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
                        contentDescription = stringResource(id = R.string.edit),
                        modifier = Modifier.clickable {
                            configurationVM.editMonthlySavings = !configurationVM.editMonthlySavings
                        }
                    )
                }
            }
        }
        MyAddIncomesAndExpensesGrid(
            navHostController = navHostController, cardItems = configurationVM.cardItems
        )
        MyCategoryList(categoriesVM.categories)
        if (configurationVM.editMonthlySavings) {
            AppModalBottomSheet(onDismissRequest = {
                configurationVM.monthlySavingsInputValue = ""
                configurationVM.editMonthlySavings = !configurationVM.editMonthlySavings
            }) {
                MyModalMonthlySavingModalBottomSheet(
                    onClickNegative = {
                        configurationVM.monthlySavingsInputValue = ""
                        configurationVM.editMonthlySavings = !configurationVM.editMonthlySavings
                    },
                    onClickPositive = {
                        userVM.viewModelScope.launch {
                            userVM.updateUser(
                                AppUtils.getAppId(context),
                                configurationVM.monthlySavingsInputValue.toInt()
                            )
                        }
                        configurationVM.editMonthlySavings = ! configurationVM.editMonthlySavings
                        configurationVM.monthlySavingsInputValue = ""
                    }
                )
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

fun resetInputs(dropdownVM: ExposedDropDownViewModel, categoryVM: CategoryViewModel) {
    dropdownVM.dropdownValue = ""
    categoryVM.categoryName = ""
}
