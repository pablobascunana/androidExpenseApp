package com.pbs.expenseApp.ui.composables.addmonthlymovements

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.pbs.expenseApp.R
import com.pbs.expenseApp.domain.model.CategoryType
import com.pbs.expenseApp.ui.AppViewModelProvider
import com.pbs.expenseApp.ui.components.AppButton
import com.pbs.expenseApp.ui.components.AppColumn
import com.pbs.expenseApp.ui.components.AppRow
import com.pbs.expenseApp.ui.components.AppText
import com.pbs.expenseApp.ui.components.AppTextField
import com.pbs.expenseApp.ui.viewmodels.ExpenseViewModel
import com.pbs.expenseApp.utils.AppUtils

@Composable
fun MyAddExpenseModalBottomSheet(
    navHostController: NavHostController,
    onClickNegative: () -> Unit,
    onClickPositive: () -> Unit,
) {
    val context = LocalContext.current
    val expenseVM: ExpenseViewModel = viewModel(factory = AppViewModelProvider.Factory)

    val currentType =
        navHostController.currentBackStackEntry?.arguments?.getString("type") ?: ""

    expenseVM.movementType = AppUtils.categoryTypeToEnum(context, currentType)

    var text = stringResource(id = R.string.add_monthly_expense)
    if (expenseVM.movementType == CategoryType.INCOME) {
        text = stringResource(id = R.string.add_monthly_income)
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
        MyCategoryExposedDropdownMenuBox()
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = dimensionResource(id = R.dimen.padding_sm)),
            value = expenseVM.amountValue,
            onValueChange = { expenseVM.amountValue = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        AppRow(modifier = Modifier
            .align(Alignment.End)
            .padding(top = dimensionResource(id = R.dimen.padding_sm))
        ) {
            AppButton(
                onClick = { onClickNegative() },
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.errorContainer)
            ) {
                AppText(
                    text = stringResource(id = R.string.cancel),
                    color = MaterialTheme.colorScheme.onErrorContainer
                )
            }
            Spacer(Modifier.size(dimensionResource(id = R.dimen.padding_xs)))
            AppButton(
                enabled = expenseVM.categoryName.isNotEmpty() &&
                        expenseVM.descriptionValue.isNotEmpty() &&
                        expenseVM.amountValue.isNotEmpty(),
                onClick = { onClickPositive() },
                buttonContent = {
                    AppText(
                        text = stringResource(id = R.string.save),
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            )
        }
    }
}
