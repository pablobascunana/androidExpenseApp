package com.pbs.expenseApp.ui.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pbs.expenseApp.R
import com.pbs.expenseApp.domain.model.CategoryType
import com.pbs.expenseApp.ui.AppViewModelProvider
import com.pbs.expenseApp.ui.components.AppIcon
import com.pbs.expenseApp.ui.components.AppModalBottomSheet
import com.pbs.expenseApp.ui.screens.resetInputs
import com.pbs.expenseApp.ui.viewmodels.CategoryViewModel
import com.pbs.expenseApp.ui.viewmodels.ConfigurationViewModel
import kotlinx.coroutines.async

@Composable
fun MyAddCategoryFab() {
    val configurationVM: ConfigurationViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val categoryVM: CategoryViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val categoryType: CategoryType?

    FloatingActionButton(
        onClick = {
            configurationVM.addCategory = !configurationVM.addCategory
        },
    ) {
        AppIcon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(id = R.string.configuration_add_category)
        )
    }

    if(configurationVM.addCategory) {
        AppModalBottomSheet(onDismissRequest = {
            configurationVM.addCategory = !configurationVM.addCategory
        }
        ) {
            MyConfigurationModalBottomSheet(
                text = stringResource(id = R.string.configuration_add_new_category),
                onClickNegative = {
                    resetInputs(categoryVM)
                    configurationVM.addCategory = !configurationVM.addCategory
                },
                onClickPositive = {
                    categoryVM.canSaveCategory()
                }
            )
        }
    }
    if (categoryVM.canSaveCategory) {
        categoryType = categoryTypeToEnumValue(type = categoryVM.categoryType)
        LaunchedEffect(key1 = 1) {
            async {
                categoryVM.saveCategory(categoryVM.categoryName, categoryType)
                resetInputs(categoryVM)
                categoryVM.canSaveCategory()
                configurationVM.addCategory = !configurationVM.addCategory
            }.await()
        }
    }
}

@Composable
fun categoryTypeToEnumValue(type: String): CategoryType {
    return when (type) {
        stringResource(id = R.string.category_type_income) -> CategoryType.INCOME
        else -> CategoryType.EXPENSE
    }
}
