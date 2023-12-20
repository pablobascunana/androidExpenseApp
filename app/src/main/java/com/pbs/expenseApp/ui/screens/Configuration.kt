package com.pbs.expenseApp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pbs.expenseApp.R
import com.pbs.expenseApp.ui.AppViewModelProvider
import com.pbs.expenseApp.ui.components.AppCard
import com.pbs.expenseApp.ui.components.AppColumn
import com.pbs.expenseApp.ui.components.AppIcon
import com.pbs.expenseApp.ui.components.AppRow
import com.pbs.expenseApp.ui.components.AppText
import com.pbs.expenseApp.ui.composables.MyMonthlySavingText
import com.pbs.expenseApp.ui.viewmodels.ConfigurationViewModel
import com.pbs.expenseApp.ui.viewmodels.UserViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun Configuration() {
    val userVM: UserViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val appVM: AppViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val configurationMV: ConfigurationViewModel = viewModel(factory = AppViewModelProvider.Factory)

    val monthlySavings = userVM.monthlySavings.observeAsState()

    val monthlySavingsInputValue = configurationMV.monthlySavingsInputValue.observeAsState()
    val decimalPattern = configurationMV.decimalPattern.observeAsState()
    val editMonthlySavings = configurationMV.editMonthlySavings.observeAsState()

    val sheetState = rememberModalBottomSheetState()

    LaunchedEffect(key1 = true) {
        async { userVM.getMonthlySavings(appVM.id) }.await()
    }

    AppColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_sm))
    ) {
        AppCard {
             AppRow(modifier = Modifier
                 .fillMaxSize()
                 .padding(
                     start = dimensionResource(id = R.dimen.padding_sm),
                     end = dimensionResource(id = R.dimen.padding_sm)
                 )
             ) {
                MyMonthlySavingText(monthlySavings = monthlySavings.value ?: 0)
                Spacer(modifier = Modifier.weight(1f))
                if (!editMonthlySavings.value!!) {
                    AppIcon(
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = "Edit icon",
                        modifier = Modifier.clickable {
                            configurationMV.canEditMonthlySavings()
                        }
                    )
                }
            }
        }
        if (editMonthlySavings.value!!) {
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
                                userVM.viewModelScope.launch {
                                    async {
                                        userVM.updateUser(
                                            appVM.id,
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
    }
}
