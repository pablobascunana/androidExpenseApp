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
import com.pbs.expenseApp.ui.viewmodels.CategoryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyCategoryExposedDropdownMenuBox() {
    val categoryVM: CategoryViewModel = viewModel(factory = AppViewModelProvider.Factory)

    ExposedDropdownMenuBox(
        expanded = categoryVM.expandedCategoryDropDown,
        modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_sm)),
        onExpandedChange = {
            categoryVM.expandedCategoryDropDown = !categoryVM.expandedCategoryDropDown
        },
    ) {
        AppTextField(
            text = stringResource(id = R.string.configuration_category_title),
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            value = categoryVM.categoryName,
            onValueChange = { categoryVM.categoryName = it },
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = categoryVM.expandedCategoryDropDown
                )},
        )
        ExposedDropdownMenu(
            expanded = categoryVM.expandedCategoryDropDown,
            onDismissRequest = { categoryVM.expandedCategoryDropDown = false }
        ) {
            categoryVM.categories.forEach { category ->
                DropdownMenuItem(
                    text = { AppText(text = category.name) },
                    onClick = {
                        categoryVM.selectedCategory = category
                        categoryVM.expandedCategoryDropDown = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}