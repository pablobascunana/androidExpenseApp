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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.pbs.expenseApp.ui.viewmodels.UserViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun Configuration() {
    val userVm: UserViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val appVM: AppViewModel = viewModel(factory = AppViewModelProvider.Factory)

    var monthlySavings by remember { mutableIntStateOf(value = 0) }
    var inputValue by remember { mutableStateOf(value = "") }
    var canEditMonthlySavings by remember { mutableStateOf(value = false) }
    val sheetState = rememberModalBottomSheetState()
    val pattern = remember { Regex(pattern = "^\\d+\$") }

    LaunchedEffect(key1 = true) {
        monthlySavings = async { userVm.getMonthlySavings(appVM.id) }.await()
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
                MyMonthlySavingText(monthlySavings)
                Spacer(modifier = Modifier.weight(1f))
                if (!canEditMonthlySavings) {
                    AppIcon(
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = "Edit icon",
                        modifier = Modifier.clickable {
                            canEditMonthlySavings = !canEditMonthlySavings
                        }
                    )
                }
            }
        }
        if (canEditMonthlySavings) {
            ModalBottomSheet(
                onDismissRequest = {
                    canEditMonthlySavings = !canEditMonthlySavings
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
                        value = inputValue,
                        onValueChange = {
                            if (it.isNotEmpty() && it.matches(pattern)) {
                                inputValue = it
                            } else if(it.isEmpty()) {
                                inputValue = ""
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
                                inputValue = ""
                                canEditMonthlySavings = !canEditMonthlySavings
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
                                userVm.viewModelScope.launch {
                                    async {
                                        // userVm.updateUser(appVM.id, inputValue.toInt())
                                        monthlySavings = userVm.getMonthlySavings(appVM.id)
                                    }.await()
                                    canEditMonthlySavings = !canEditMonthlySavings
                                }
                                inputValue = ""
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
