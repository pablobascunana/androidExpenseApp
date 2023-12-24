package com.pbs.expenseApp.ui.composables

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
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pbs.expenseApp.R
import com.pbs.expenseApp.ui.AppViewModelProvider
import com.pbs.expenseApp.ui.components.AppButton
import com.pbs.expenseApp.ui.components.AppColumn
import com.pbs.expenseApp.ui.components.AppRow
import com.pbs.expenseApp.ui.components.AppText
import com.pbs.expenseApp.ui.components.AppTextField
import com.pbs.expenseApp.ui.viewmodels.ConfigurationViewModel
import com.pbs.expenseApp.ui.viewmodels.UserViewModel
import com.pbs.expenseApp.utils.AppUtils
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@Composable
fun MyModalMonthlySavingModalBottomSheet() {
    val context = LocalContext.current

    val userVM: UserViewModel = viewModel(factory = AppViewModelProvider.Factory)
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
                if (it.isNotEmpty() && it.matches(AppUtils.getDecimalPattern())) {
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
            AppButton(
                onClick = {
                    configurationVM.monthlySavingsInputValue = ""
                    configurationVM.editMonthlySavings = !configurationVM.editMonthlySavings
                },
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.errorContainer)
            ) {
                AppText(
                    text = stringResource(id = R.string.cancel),
                    color = MaterialTheme.colorScheme.onErrorContainer
                )
            }
            Spacer(Modifier.size(dimensionResource(id = R.dimen.padding_xs)))
            AppButton(
                enabled = configurationVM.monthlySavingsInputValue.isNotEmpty(),
                onClick = {
                    userVM.viewModelScope.launch {
                        async {
                            userVM.updateUser(
                                AppUtils.getAppId(context),
                                configurationVM.monthlySavingsInputValue.toInt()
                            )
                        }.await()
                        configurationVM.editMonthlySavings = ! configurationVM.editMonthlySavings
                    }
                    configurationVM.monthlySavingsInputValue = ""
                },
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
