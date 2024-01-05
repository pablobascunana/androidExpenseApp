package com.pbs.expenseApp.ui.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pbs.expenseApp.R
import com.pbs.expenseApp.ui.AppViewModelProvider
import com.pbs.expenseApp.ui.components.AppColumn
import com.pbs.expenseApp.ui.components.AppRow
import com.pbs.expenseApp.ui.components.AppTextField
import com.pbs.expenseApp.ui.viewmodels.ConfigurationViewModel
import com.pbs.expenseApp.utils.AppUtils

@Composable
fun MyModalMonthlySavingModalBottomSheet(
    onClickNegative: () -> Unit,
    onClickPositive: () -> Unit,
) {
    val configurationVM: ConfigurationViewModel = viewModel(factory = AppViewModelProvider.Factory)

    AppColumn(
        modifier = Modifier.padding(
            start = dimensionResource(id = R.dimen.padding_sm_3),
            end = dimensionResource(id = R.dimen.padding_sm_3),
            bottom = dimensionResource(id = R.dimen.padding_sm_3)
        ),
    ) {
        AppTextField(
            value = configurationVM.monthlySavingsInputValue,
            onValueChange = {
                if (it.isNotEmpty() && it.matches(AppUtils.getIntegerPattern())) {
                    configurationVM.monthlySavingsInputValue = it
                } else if(it.isEmpty()) {
                    configurationVM.monthlySavingsInputValue = ""
                }
            },
            text = stringResource(id = R.string.configuration_save_money),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        AppRow(modifier = Modifier
            .align(Alignment.End)
            .padding(top = dimensionResource(id = R.dimen.padding_sm))
        ) {
            MyModalBottomSheetActions(
                enabled = configurationVM.monthlySavingsInputValue.isNotEmpty(),
                onClickNegative = { onClickNegative() },
                onClickPositive = { onClickPositive() }
            )
        }
    }
}
