package com.pbs.expenseApp.ui.composables.addmonthlymovements

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.pbs.expenseApp.R
import com.pbs.expenseApp.ui.AppViewModelProvider
import com.pbs.expenseApp.ui.components.AppIcon
import com.pbs.expenseApp.ui.components.AppModalBottomSheet
import com.pbs.expenseApp.ui.components.AppRow
import com.pbs.expenseApp.ui.components.AppText
import com.pbs.expenseApp.ui.viewmodels.CategoryViewModel
import com.pbs.expenseApp.ui.viewmodels.ExpenseViewModel
import com.pbs.expenseApp.ui.viewmodels.ExposedDropDownViewModel
import com.pbs.expenseApp.utils.AppUtils
import kotlinx.coroutines.async

@Composable
fun MyAddExpenseFab(navHostController: NavHostController) {
    val context = LocalContext.current
    val expenseVM: ExpenseViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val categoryVM: CategoryViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val dropdownVM: ExposedDropDownViewModel = viewModel(factory = AppViewModelProvider.Factory)

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
            resetExpenseInputs(expenseVM, dropdownVM)
            expenseVM.addExpense = !expenseVM.addExpense
        }
        ) {
            MyAddExpenseModalBottomSheet(
                navHostController = navHostController,
                onClickNegative = {
                    resetExpenseInputs(expenseVM, dropdownVM)
                    expenseVM.addExpense = !expenseVM.addExpense
                },
                onClickPositive = {
                    expenseVM.canInsertExpense = !expenseVM.canInsertExpense
                }
            )
        }
        if (expenseVM.canInsertExpense) {
            LaunchedEffect(key1 = 1) {
                async {
                    expenseVM.insert(
                        category = categoryVM.categorySelected,
                        amount = expenseVM.amount.toInt(),
                        description = expenseVM.descriptionValue,
                        payMethod = AppUtils.payMethodTypeToEnum(
                            context = context, payMethod = expenseVM.payMethodSelected
                        )
                    )
                    resetExpenseInputs(expenseVM, dropdownVM)
                    expenseVM.canInsertExpense = !expenseVM.canInsertExpense
                    expenseVM.addExpense = !expenseVM.addExpense
                    /* AppUtils.showToast(
                        context = context, textId =
                    ) */
                }.await()
            }

            /* categoryType = AppUtils.categoryTypeToEnum(
                context = context, type = categoryVM.categoryType
            )
            LaunchedEffect(key1 = 1) {
                async {
                    categoryVM.insert(categoryVM.categoryName, categoryType)
                    resetInputs(categoryVM)
                    categoryVM.canInsertCategory = !categoryVM.canInsertCategory
                    configurationVM.addCategory = !configurationVM.addCategory
                    AppUtils.showToast(
                        context = context, textId = R.string.configuration_insert_feedback
                    )
                }.await()
            }*/
        }
    }
}

private fun resetExpenseInputs(expenseVM: ExpenseViewModel, dropdownVM: ExposedDropDownViewModel) {
    dropdownVM.dropdownValue = ""
    expenseVM.descriptionValue = ""
    expenseVM.amount = ""
}
