package com.pbs.expenseApp.ui.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.lifecycle.viewModelScope
import com.pbs.expenseApp.R
import com.pbs.expenseApp.ui.components.AppButton
import com.pbs.expenseApp.ui.components.AppColumn
import com.pbs.expenseApp.ui.components.AppModalBottomSheet
import com.pbs.expenseApp.ui.components.AppRow
import com.pbs.expenseApp.ui.components.AppText
import com.pbs.expenseApp.ui.components.AppTextField
import com.pbs.expenseApp.ui.viewmodels.ConfigurationViewModel
import com.pbs.expenseApp.ui.viewmodels.UserViewModel
import com.pbs.expenseApp.utils.AppUtils
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@Composable
fun MyMonthlySavingModalBottomSheet(
    userVM: UserViewModel,
    configurationMV: ConfigurationViewModel
) {
    val context = LocalContext.current

    val monthlySavingsInputValue = configurationMV.monthlySavingsInputValue.observeAsState()
    val decimalPattern = configurationMV.decimalPattern.observeAsState()

    AppModalBottomSheet(onDismissRequest = { configurationMV.canEditMonthlySavings() }) {
        AppColumn(
            modifier = Modifier.padding(
                start = dimensionResource(id = R.dimen.padding_sm_3),
                end = dimensionResource(id = R.dimen.padding_sm_3),
                bottom = dimensionResource(id = R.dimen.padding_sm_3)
            ),
        ) {
            AppTextField(
                value = monthlySavingsInputValue.value!!,
                onValueChange = {
                    if (it.isNotEmpty() && it.matches(decimalPattern.value!!)) {
                        configurationMV.setMonthlySavingsInputValue(it)
                    } else if(it.isEmpty()) {
                        configurationMV.setMonthlySavingsInputValue()
                    }
                },
                textId = R.string.configuration_save_money
            )
            AppRow(modifier = Modifier
                .align(Alignment.End)
                .padding(top = dimensionResource(id = R.dimen.padding_sm))
            ) {
                AppButton(
                    onClick = {
                        configurationMV.setMonthlySavingsInputValue()
                        configurationMV.canEditMonthlySavings()
                    },
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.errorContainer)
                ) {
                    AppText(
                        id = R.string.cancel,
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
                Spacer(Modifier.size(dimensionResource(id = R.dimen.padding_xs)))
                AppButton(
                    onClick = {
                        userVM.viewModelScope.launch {
                            async {
                                userVM.updateUser(
                                    AppUtils.appId(context),
                                    monthlySavingsInputValue.value!!.toInt()
                                )
                            }.await()
                            configurationMV.canEditMonthlySavings()
                        }
                        configurationMV.setMonthlySavingsInputValue()
                    },
                    buttonContent = {
                        AppText(
                            id = R.string.save,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                )
            }
        }
    }
}
