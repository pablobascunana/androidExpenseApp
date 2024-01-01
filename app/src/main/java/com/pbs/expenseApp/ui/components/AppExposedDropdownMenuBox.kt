package com.pbs.expenseApp.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuBoxScope
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pbs.expenseApp.ui.AppViewModelProvider
import com.pbs.expenseApp.ui.viewmodels.ExposedDropDownViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppExposedDropdownMenuBox(
    text: String,
    exposedDropdownContent: @Composable ExposedDropdownMenuBoxScope.() -> Unit
) {
    val dropdownVM: ExposedDropDownViewModel = viewModel(factory = AppViewModelProvider.Factory)

    ExposedDropdownMenuBox(
        expanded = dropdownVM.dropdownExpanded,
        onExpandedChange = { dropdownVM.dropdownExpanded = !dropdownVM.dropdownExpanded }
    ) {
        AppTextField(
            text = text,
            modifier = Modifier.fillMaxWidth().menuAnchor(),
            value = dropdownVM.dropdownValue,
            onValueChange = { dropdownVM.dropdownValue = it },
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropdownVM.dropdownExpanded)
            }
        )
        ExposedDropdownMenu(
            expanded = dropdownVM.dropdownExpanded,
            onDismissRequest = { dropdownVM.dropdownExpanded = !dropdownVM.dropdownExpanded }
        ) {
            exposedDropdownContent()
        }
    }
}
