package com.pbs.expenseApp.ui.composables.addcategories

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pbs.expenseApp.R
import com.pbs.expenseApp.domain.model.CategoryType
import com.pbs.expenseApp.ui.AppViewModelProvider
import com.pbs.expenseApp.ui.components.AppIcon
import com.pbs.expenseApp.ui.components.AppModalBottomSheet
import com.pbs.expenseApp.ui.components.AppRow
import com.pbs.expenseApp.ui.components.AppText
import com.pbs.expenseApp.ui.screens.resetInputs
import com.pbs.expenseApp.ui.viewmodels.CategoryViewModel
import com.pbs.expenseApp.ui.viewmodels.ConfigurationViewModel
import com.pbs.expenseApp.utils.AppUtils
import kotlinx.coroutines.async

@Composable
fun MyAddCategoryFab() {
    val context = LocalContext.current

    val configurationVM: ConfigurationViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val categoryVM: CategoryViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val categoryType: CategoryType?

    FloatingActionButton(
        onClick = {
            configurationVM.addCategory = !configurationVM.addCategory
        },
    ) {
        AppRow(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_xs))
        ) {
            AppIcon(
                imageVector = Icons.Filled.Add,
                contentDescription = stringResource(id = R.string.configuration_add_category)
            )
            AppText(text = stringResource(id = R.string.configuration_add_category))
        }
    }
    if(configurationVM.addCategory) {
        AppModalBottomSheet(onDismissRequest = {
            resetInputs(categoryVM)
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
                    categoryVM.canInsertCategory = !categoryVM.canInsertCategory
                }
            )
        }
    }
    if (categoryVM.canInsertCategory) {
        categoryType = AppUtils.categoryTypeToEnum(
            context = context, category = categoryVM.categoryType
        )
        LaunchedEffect(key1 = 1) {
            async {
                categoryVM.insert(categoryVM.categoryName, categoryType)
                resetInputs(categoryVM)
                categoryVM.canInsertCategory = !categoryVM.canInsertCategory
                configurationVM.addCategory = !configurationVM.addCategory
                AppUtils.showToast(
                    context = context, textId = R.string.configuration_insert_feedback
                )
            }.await()
        }
    }
}
