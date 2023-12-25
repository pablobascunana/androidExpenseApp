package com.pbs.expenseApp.ui.composables.addcategories

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pbs.expenseApp.R
import com.pbs.expenseApp.ui.AppViewModelProvider
import com.pbs.expenseApp.ui.components.AppText
import com.pbs.expenseApp.ui.components.AppTextField
import com.pbs.expenseApp.ui.viewmodels.CategoryViewModel
import com.pbs.expenseApp.utils.AppUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyCategoryTypeExposedDropdownMenuBox() {
    val context = LocalContext.current
    val categoryVM: CategoryViewModel = viewModel(factory = AppViewModelProvider.Factory)

    val categoryTypes = AppUtils.categoryTypesToString(
        context = context, types = categoryVM.categoryTypes
    )
    val expandedCategoryTypeDropDown = categoryVM.expandedCategoryTypeDropDown

    ExposedDropdownMenuBox(
        expanded = expandedCategoryTypeDropDown,
        onExpandedChange = {
            categoryVM.expandedCategoryTypeDropDown = !categoryVM.expandedCategoryTypeDropDown
        },
    ) {
        AppTextField(
            text = stringResource(id = R.string.configuration_category_type),
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            value = categoryVM.categoryType,
            onValueChange = { categoryVM.categoryType = it },
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expandedCategoryTypeDropDown
                )},
        )
        ExposedDropdownMenu(
            expanded = expandedCategoryTypeDropDown,
            onDismissRequest = { categoryVM.expandedCategoryTypeDropDown = false }
        ) {
            categoryTypes.forEach { categoryType ->
                DropdownMenuItem(
                    text = { AppText(text = categoryType.value) },
                    onClick = {
                        categoryVM.categoryType = categoryType.value
                        categoryVM.expandedCategoryTypeDropDown = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}
