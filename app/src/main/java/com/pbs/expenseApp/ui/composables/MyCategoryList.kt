package com.pbs.expenseApp.ui.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pbs.expenseApp.R
import com.pbs.expenseApp.domain.model.Category
import com.pbs.expenseApp.ui.AppViewModelProvider
import com.pbs.expenseApp.ui.components.AppAlertDialog
import com.pbs.expenseApp.ui.components.AppList
import com.pbs.expenseApp.ui.components.AppModalBottomSheet
import com.pbs.expenseApp.ui.components.AppText
import com.pbs.expenseApp.ui.screens.resetInputs
import com.pbs.expenseApp.ui.viewmodels.CategoryViewModel
import com.pbs.expenseApp.ui.viewmodels.ConfigurationViewModel
import com.pbs.expenseApp.utils.AppUtils
import kotlinx.coroutines.async

@Composable
fun MyCategoryList(categories: List<Category>) {
    val context = LocalContext.current

    val configurationVM: ConfigurationViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val categoryVM: CategoryViewModel = viewModel(factory = AppViewModelProvider.Factory)

    AppText(
        text = stringResource(id = R.string.configuration_category_title),
        fontSize = fontDimensionResource(id = R.dimen.font_md),
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_sm))
    )
    AppList(
        categories,
        onEdit = {
            categoryVM.categoryType = AppUtils.categoryTypeToString(
                context, it.type.value
            )
            categoryVM.categoryName = it.name
            categoryVM.selectedCategory = it
            configurationVM.editCategory = !configurationVM.editCategory
        },
        onDelete = {
            categoryVM.categoryName = it.name
            categoryVM.selectedCategory = it
            categoryVM.canDeleteCategory = !categoryVM.canDeleteCategory
        }
    )
    if (configurationVM.editCategory) {
        AppModalBottomSheet(onDismissRequest = {
            configurationVM.editCategory = !configurationVM.editCategory
        }
        ) {
            MyConfigurationModalBottomSheet(
                text = stringResource(id = R.string.configuration_edit_category),
                onClickNegative = {
                    resetInputs(categoryVM)
                    configurationVM.editCategory = !configurationVM.editCategory
                },
                onClickPositive = {
                    categoryVM.canEditCategory = !categoryVM.canEditCategory
                }
            )
        }
    }
    if (categoryVM.canEditCategory) {
        categoryVM.selectedCategory.name = categoryVM.categoryName
        categoryVM.selectedCategory.type = AppUtils.categoryTypeToEnum(
            context = context, type = categoryVM.categoryType
        )
        LaunchedEffect(key1 = 1) {
            async {
                categoryVM.update(categoryVM.selectedCategory)
                resetInputs(categoryVM)
                categoryVM.canEditCategory = !categoryVM.canEditCategory
                configurationVM.editCategory = !configurationVM.editCategory
                AppUtils.showToast(context = context, textId = R.string.configuration_edit_feedback)
            }.await()
        }
    }
    if (categoryVM.canDeleteCategory) {
        AppAlertDialog(
            dialogTitle = stringResource(id = R.string.configuration_delete_category),
            dialogText = stringResource(
                id = R.string.configuration_delete_category_confirmation
            ) + " ${categoryVM.categoryName}?",
            icon = Icons.Outlined.Delete,
            onConfirmation = { categoryVM.confirmDelete = !categoryVM.confirmDelete },
            onDismissRequest = { categoryVM.canDeleteCategory = !categoryVM.canDeleteCategory },
        )
        if (categoryVM.confirmDelete) {
            LaunchedEffect(key1 = 1) {
                async {
                    categoryVM.delete(categoryVM.selectedCategory)
                    categoryVM.canDeleteCategory = !categoryVM.canDeleteCategory
                    categoryVM.confirmDelete = !categoryVM.confirmDelete
                    AppUtils.showToast(
                        context = context,
                        textId = R.string.configuration_delete_feedback
                    )
                }.await()
            }
        }
    }
}
