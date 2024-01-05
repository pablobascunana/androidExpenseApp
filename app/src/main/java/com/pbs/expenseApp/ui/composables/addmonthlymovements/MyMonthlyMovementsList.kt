package com.pbs.expenseApp.ui.composables.addmonthlymovements

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.pbs.expenseApp.R
import com.pbs.expenseApp.ui.AppViewModelProvider
import com.pbs.expenseApp.ui.components.AppAlertDialog
import com.pbs.expenseApp.ui.components.AppColumn
import com.pbs.expenseApp.ui.components.AppList
import com.pbs.expenseApp.ui.components.AppModalBottomSheet
import com.pbs.expenseApp.ui.components.AppText
import com.pbs.expenseApp.ui.composables.fontDimensionResource
import com.pbs.expenseApp.ui.viewmodels.CategoryViewModel
import com.pbs.expenseApp.ui.viewmodels.ExpenseViewModel
import com.pbs.expenseApp.ui.viewmodels.ExposedDropDownViewModel
import com.pbs.expenseApp.utils.AppUtils
import kotlinx.coroutines.launch

@Composable
fun MyMonthlyMovementsList(
    navHostController: NavHostController
) {
    val context = LocalContext.current
    val categoryVM: CategoryViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val expenseVM: ExpenseViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val dropdownVM: ExposedDropDownViewModel = viewModel(factory = AppViewModelProvider.Factory)

    val currentType =
        navHostController.currentBackStackEntry?.arguments?.getString("type") ?: ""

    expenseVM.movementType = categoryVM.categoryTypeToEnum(currentType)

    LaunchedEffect(key1 = 1) {
        expenseVM.viewModelScope.launch { expenseVM.getExpenseList() }
    }

    AppColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_sm))
    ){
        AppText(
            text = expenseVM.getCreateExpenseText(),
            fontSize = fontDimensionResource(id = R.dimen.font_md),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_sm))
        )
        if (expenseVM.expenses.isEmpty()) {
            AppText(
                text = stringResource(id = R.string.no_items_in_list),
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentHeight(align = Alignment.CenterVertically),
                textAlign = TextAlign.Center
            )
        } else {
            AppList(
                items = expenseVM.expenses,
                onEdit = {
                    expenseVM.expenseSelected = it
                    expenseVM.canEdit = !expenseVM.canEdit
                },
                onDelete = {
                    expenseVM.expenseSelected = it
                    expenseVM.canDelete = !expenseVM.canDelete
                }
            )
        }
    }
    if (expenseVM.canEdit) {
        LaunchedEffect(key1 = 1) {
            categoryVM.viewModelScope.launch {
                categoryVM.getCategory(expenseVM.expenseSelected.categoryUuid)
            }
            dropdownVM.dropdownValue = categoryVM.categorySelected.name
            expenseVM.payMethodSelected = expenseVM.expenseSelected.payMethod.value
            expenseVM.descriptionValue = expenseVM.expenseSelected.description
            expenseVM.amount = expenseVM.expenseSelected.amount.toString()
        }
        AppModalBottomSheet(onDismissRequest = {
            resetExpenseInputs(expenseVM, dropdownVM)
            expenseVM.canEdit = !expenseVM.canEdit
        }) {
            MyAddExpenseModalBottomSheet(
                navHostController = navHostController,
                text = expenseVM.getEditExpenseText(),
                onClickNegative = {
                    resetExpenseInputs(expenseVM, dropdownVM)
                    expenseVM.canEdit = !expenseVM.canEdit
                },
                onClickPositive = {
                    expenseVM.confirmEdit = !expenseVM.confirmEdit
                }
            )
        }
    }
    if (expenseVM.confirmEdit) {
        LaunchedEffect(key1 = 1) {
            expenseVM.viewModelScope.launch {
                expenseVM.update(
                    category = categoryVM.categorySelected,
                    uuid = expenseVM.expenseSelected.uuid,
                    amount = expenseVM.amount.toFloat(),
                    description = expenseVM.descriptionValue,
                    payMethod = expenseVM.payMethodTypeToEnum()
                )
            }
            resetExpenseInputs(expenseVM, dropdownVM)
            expenseVM.canEdit = !expenseVM.canEdit
            expenseVM.confirmEdit = !expenseVM.confirmEdit
            AppUtils.showToast(context = context, textId = R.string.expense_registry_edit)
        }
    }
    if (expenseVM.canDelete) {
        AppAlertDialog(
            dialogText = stringResource(id = R.string.expense_delete) +
                    " ${expenseVM.expenseSelected.description}" +
                    " - ${expenseVM.expenseSelected.amount}",
            icon = Icons.Outlined.Delete,
            onConfirmation = { expenseVM.confirmDelete = !expenseVM.confirmDelete },
            onDismissRequest = { expenseVM.canDelete = !expenseVM.canDelete },
        )
    }
    if (expenseVM.confirmDelete) {
        LaunchedEffect(key1 = 1) {
            expenseVM.viewModelScope.launch {
                expenseVM.delete(expense = expenseVM.expenseSelected)
            }
            expenseVM.canDelete = !expenseVM.canDelete
        }
        AppUtils.showToast(context = context, textId = R.string.expense_registry_delete)
    }
}
