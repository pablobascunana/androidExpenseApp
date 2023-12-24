package com.pbs.expenseApp.ui.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pbs.expenseApp.R
import com.pbs.expenseApp.domain.model.CategoryType
import com.pbs.expenseApp.ui.AppViewModelProvider
import com.pbs.expenseApp.ui.components.AppText
import com.pbs.expenseApp.ui.components.AppTextField
import com.pbs.expenseApp.ui.screens.resetInputs
import com.pbs.expenseApp.ui.viewmodels.CategoryViewModel
import com.pbs.expenseApp.ui.viewmodels.ConfigurationViewModel
import kotlinx.coroutines.async

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyCategoryTypeExposedDropdownMenuBox() {
    val categoryVM: CategoryViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val configurationVM: ConfigurationViewModel = viewModel(factory = AppViewModelProvider.Factory)

    val categoryTypes = formatCategoryTypes(categoryVM.categoryTypes)
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

@Composable
private fun formatCategoryTypes(categoryTypes: Array<CategoryType>): Array<CategoryType> {
    categoryTypes.forEach { category ->
        when (category.value) {
            CategoryType.INCOME.value ->
                category.value = stringResource(id = R.string.category_type_income)
            else -> category.value = stringResource(id = R.string.category_type_expense)
        }
    }
    return categoryTypes
}
