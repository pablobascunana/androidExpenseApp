package com.pbs.expenseApp.ui.composables.addmonthlymovements

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.pbs.expenseApp.R
import com.pbs.expenseApp.ui.AppViewModelProvider
import com.pbs.expenseApp.ui.components.AppColumn
import com.pbs.expenseApp.ui.components.AppExposedDropdownMenuBox
import com.pbs.expenseApp.ui.components.AppRow
import com.pbs.expenseApp.ui.components.AppText
import com.pbs.expenseApp.ui.components.AppTextField
import com.pbs.expenseApp.ui.composables.MyModalBottomSheetActions
import com.pbs.expenseApp.ui.viewmodels.CategoryViewModel
import com.pbs.expenseApp.ui.viewmodels.ExpenseViewModel
import com.pbs.expenseApp.ui.viewmodels.ExposedDropDownViewModel
import com.pbs.expenseApp.utils.AppUtils
import kotlinx.coroutines.async

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAddExpenseModalBottomSheet(
    navHostController: NavHostController,
    text: String,
    onClickNegative: () -> Unit,
    onClickPositive: () -> Unit,
) {
    val expenseVM: ExpenseViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val categoryVM: CategoryViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val dropdownVM: ExposedDropDownViewModel = viewModel(factory = AppViewModelProvider.Factory)

    val currentType =
        navHostController.currentBackStackEntry?.arguments?.getString("type") ?: ""

    expenseVM.movementType = categoryVM.categoryTypeToEnum(currentType)

    LaunchedEffect(key1 = 1) {
        categoryVM.viewModelScope.async {
            categoryVM.getCategoryByCategoryType(expenseVM.movementType)
        }.await()
    }

    AppColumn(
        modifier = Modifier.padding(
            start = dimensionResource(id = R.dimen.padding_sm_3),
            end = dimensionResource(id = R.dimen.padding_sm_3),
            bottom = dimensionResource(id = R.dimen.padding_sm_3)
        ),
    ) {
        AppText(
            text = text,
            modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_sm))
        )
        AppExposedDropdownMenuBox(
            text = stringResource(id = R.string.configuration_category_title),
            modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_sm))
        ) {
            categoryVM.categories.forEach { category ->
                DropdownMenuItem(
                    text = { AppText(text = category.name) },
                    onClick = {
                        dropdownVM.dropdownValue = category.name
                        categoryVM.categorySelected = category
                        dropdownVM.dropdownExpanded = !dropdownVM.dropdownExpanded
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
        MyPayMethodExposedDropdownMenuBox()
        AppTextField(
            text = stringResource(id = R.string.expense_description),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = dimensionResource(id = R.dimen.padding_sm)),
            value = expenseVM.descriptionValue,
            onValueChange = { expenseVM.descriptionValue = it }
        )
        AppTextField(
            text = stringResource(id = R.string.expense_amount),
            modifier = Modifier.fillMaxWidth(),
            value = expenseVM.amount,
            onValueChange = {
                if (it.isNotEmpty() && it.matches(AppUtils.getDecimalPattern())) {
                    expenseVM.amount = it
                } else if(it.isEmpty()) {
                    expenseVM.amount = ""
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        AppRow(modifier = Modifier
            .align(Alignment.End)
            .padding(top = dimensionResource(id = R.dimen.padding_sm))
        ) {
            MyModalBottomSheetActions(
                enabled = dropdownVM.dropdownValue.isNotEmpty() &&
                        expenseVM.payMethodSelected.isNotEmpty() &&
                        expenseVM.descriptionValue.isNotEmpty() &&
                        expenseVM.amount.isNotEmpty(),
                onClickNegative = { onClickNegative() },
                onClickPositive = { onClickPositive() }
            )
        }
    }
}
