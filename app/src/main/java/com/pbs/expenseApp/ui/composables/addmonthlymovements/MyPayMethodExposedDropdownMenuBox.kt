package com.pbs.expenseApp.ui.composables.addmonthlymovements

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pbs.expenseApp.R
import com.pbs.expenseApp.ui.AppViewModelProvider
import com.pbs.expenseApp.ui.components.AppText
import com.pbs.expenseApp.ui.components.AppTextField
import com.pbs.expenseApp.ui.viewmodels.ExpenseViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPayMethodExposedDropdownMenuBox() {
    val expenseVM: ExpenseViewModel = viewModel(factory = AppViewModelProvider.Factory)

    val payMethods = expenseVM.getPayMethods()

    ExposedDropdownMenuBox(
        expanded = expenseVM.expandedPayMethodDropDown,
        modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_sm)),
        onExpandedChange = {
            expenseVM.expandedPayMethodDropDown = !expenseVM.expandedPayMethodDropDown
        }
    ) {
        AppTextField(
            text = stringResource(id = R.string.pay_method),
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            value = expenseVM.payMethodSelected,
            onValueChange = { expenseVM.payMethodSelected = it },
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expenseVM.expandedPayMethodDropDown
                )},
        )
        ExposedDropdownMenu(
            expanded = expenseVM.expandedPayMethodDropDown,
            onDismissRequest = { expenseVM.expandedPayMethodDropDown = false }
        ) {
            payMethods.forEach { payMethod ->
                DropdownMenuItem(
                    text = { AppText(text = payMethod.value) },
                    onClick = {
                        expenseVM.payMethodSelected = payMethod.value
                        expenseVM.expandedPayMethodDropDown = !expenseVM.expandedPayMethodDropDown
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}
