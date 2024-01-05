package com.pbs.expenseApp.ui.composables.addcategories

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
import com.pbs.expenseApp.ui.composables.fontDimensionResource
import com.pbs.expenseApp.ui.screens.resetInputs
import com.pbs.expenseApp.ui.viewmodels.CategoryViewModel
import com.pbs.expenseApp.ui.viewmodels.ConfigurationViewModel
import com.pbs.expenseApp.ui.viewmodels.ExposedDropDownViewModel
import com.pbs.expenseApp.utils.AppUtils
import kotlinx.coroutines.async

@Composable
fun MyCategoryList(categories: List<Category>) {
    val context = LocalContext.current

    val configurationVM: ConfigurationViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val categoryVM: CategoryViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val dropdownVM: ExposedDropDownViewModel = viewModel(factory = AppViewModelProvider.Factory)

    AppText(
        text = stringResource(id = R.string.configuration_category_title),
        fontSize = fontDimensionResource(id = R.dimen.font_md),
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_sm))
    )
    AppList(
        categories,
        onEdit = {
            dropdownVM.dropdownValue = categoryVM.categoryTypeToString(
                context, it.type.value
            )
            categoryVM.categoryName = it.name
            categoryVM.categorySelected = it
            configurationVM.editCategory = !configurationVM.editCategory
        },
        onDelete = {
            categoryVM.categoryName = it.name
            categoryVM.categorySelected = it
            categoryVM.canDeleteCategory = !categoryVM.canDeleteCategory
        }
    )
    if (configurationVM.editCategory) {
        AppModalBottomSheet(onDismissRequest = {
            resetInputs(dropdownVM, categoryVM)
            configurationVM.editCategory = !configurationVM.editCategory
        }
        ) {
            MyCategoryModalBottomSheet(
                text = stringResource(id = R.string.configuration_edit_category),
                onClickNegative = {
                    resetInputs(dropdownVM, categoryVM)
                    configurationVM.editCategory = !configurationVM.editCategory
                },
                onClickPositive = {
                    categoryVM.canEditCategory = !categoryVM.canEditCategory
                }
            )
        }
    }
    if (categoryVM.canEditCategory) {
        categoryVM.categorySelected.name = categoryVM.categoryName
        categoryVM.categorySelected.type = categoryVM.categoryTypeToEnum(
            category = dropdownVM.dropdownValue
        )
        LaunchedEffect(key1 = 1) {
            async {
                categoryVM.update(categoryVM.categorySelected)
                resetInputs(dropdownVM, categoryVM)
                categoryVM.canEditCategory = !categoryVM.canEditCategory
                configurationVM.editCategory = !configurationVM.editCategory
                AppUtils.showToast(context = context, textId = R.string.configuration_edit_feedback)
            }.await()
        }
    }
    if (categoryVM.canDeleteCategory) {
        AppAlertDialog(
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
                    categoryVM.delete(categoryVM.categorySelected)
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
