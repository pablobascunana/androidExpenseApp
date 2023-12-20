package com.pbs.expenseApp.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewModelScope
import com.pbs.expenseApp.R
import com.pbs.expenseApp.ui.viewmodels.ConfigurationViewModel
import com.pbs.expenseApp.ui.viewmodels.UserViewModel
import com.pbs.expenseApp.utils.AppUtils
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppModalBottomSheet(userMV: UserViewModel, configurationMV: ConfigurationViewModel) {
    val context = LocalContext.current

    val monthlySavingsInputValue = configurationMV.monthlySavingsInputValue.observeAsState()
    val decimalPattern = configurationMV.decimalPattern.observeAsState()

    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = {
            configurationMV.canEditMonthlySavings()
        },
        sheetState = sheetState
    ) {
        AppColumn(
            modifier = Modifier.padding(
                start = dimensionResource(id = R.dimen.padding_sm_3),
                end = dimensionResource(id = R.dimen.padding_sm_3),
                bottom = dimensionResource(id = R.dimen.padding_sm_3)
            ),
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = monthlySavingsInputValue.value!!,
                onValueChange = {
                    if (it.isNotEmpty() && it.matches(decimalPattern.value!!)) {
                        configurationMV.setMonthlySavingsInputValue(it)
                    } else if(it.isEmpty()) {
                        configurationMV.setMonthlySavingsInputValue()
                    }
                },
                label = {
                    AppText(id = R.string.configuration_save_money)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            AppRow(modifier = Modifier
                .align(Alignment.End)
                .padding(top = dimensionResource(id = R.dimen.padding_sm))
            ) {
                // TODO create AppButton composable
                Button(
                    onClick = {
                        configurationMV.setMonthlySavingsInputValue()
                        configurationMV.canEditMonthlySavings()
                    },
                    colors = ButtonDefaults.buttonColors(
                        MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    AppText(
                        id = R.string.cancel,
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
                Spacer(Modifier.size(dimensionResource(id = R.dimen.padding_xs)))
                Button(
                    onClick = {
                        userMV.viewModelScope.launch {
                            async {
                                userMV.updateUser(
                                    AppUtils.appId(context),
                                    monthlySavingsInputValue.value!!.toInt()
                                )
                            }.await()
                            configurationMV.canEditMonthlySavings()
                        }
                        configurationMV.setMonthlySavingsInputValue()
                    },
                    colors = ButtonDefaults.buttonColors(
                        MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    AppText(
                        id = R.string.save,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
        }
    }
}
