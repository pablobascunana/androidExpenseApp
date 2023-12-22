package com.pbs.expenseApp.ui.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pbs.expenseApp.R
import com.pbs.expenseApp.ui.AppViewModelProvider
import com.pbs.expenseApp.ui.components.AppText
import com.pbs.expenseApp.ui.components.AppTextField
import com.pbs.expenseApp.ui.viewmodels.CategoryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyCategoryTypeExposedDropdownMenuBox() {
    val categoryVM: CategoryViewModel = viewModel(factory = AppViewModelProvider.Factory)

    val categoryTypes = formatCategoryTypes(categoryVM.categoryTypes)
    val expandedCategoryTypeDropDown = categoryVM.expandedCategoryTypeDropDown.observeAsState()

    val categoryType = categoryVM.categoryType.observeAsState()

    ExposedDropdownMenuBox(
        expanded = expandedCategoryTypeDropDown.value!!,
        onExpandedChange = { categoryVM.isExpandedCategoryTypeDropdown() },
    ) {
        AppTextField(
            text = stringResource(id = R.string.configuration_category_type),
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            value = categoryType.value!!,
            onValueChange = { categoryVM.setCategoryType(it) },
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expandedCategoryTypeDropDown.value!!
                )},
        )
        ExposedDropdownMenu(
            expanded = expandedCategoryTypeDropDown.value!!,
            onDismissRequest = { categoryVM.setExpandedCategoryTypeDropdown(false) }
        ) {
            categoryTypes.forEach { categoryType ->
                DropdownMenuItem(
                    text = { AppText(text = categoryType.value) },
                    onClick = {
                        categoryVM.setCategoryType(categoryType.value)
                        categoryVM.setExpandedCategoryTypeDropdown(false)
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}
