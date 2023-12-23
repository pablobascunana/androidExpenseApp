package com.pbs.expenseApp.ui.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pbs.expenseApp.R
import com.pbs.expenseApp.database.entities.CategoryType
import com.pbs.expenseApp.ui.AppViewModelProvider
import com.pbs.expenseApp.ui.components.AppButton
import com.pbs.expenseApp.ui.components.AppColumn
import com.pbs.expenseApp.ui.components.AppRow
import com.pbs.expenseApp.ui.components.AppText
import com.pbs.expenseApp.ui.components.AppTextField
import com.pbs.expenseApp.ui.viewmodels.CategoryViewModel
import com.pbs.expenseApp.ui.viewmodels.ConfigurationViewModel
import kotlinx.coroutines.async

@Composable
fun MyAddCategoryModalBottomSheet() {

    val categoryVM: CategoryViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val configurationVM: ConfigurationViewModel = viewModel(factory = AppViewModelProvider.Factory)

    val categoryName = categoryVM.categoryName.observeAsState()
    val saveCategory = categoryVM.canSaveCategory.observeAsState()
    var categoryType: CategoryType?

    AppColumn(
        modifier = Modifier.padding(
            start = dimensionResource(id = R.dimen.padding_sm_3),
            end = dimensionResource(id = R.dimen.padding_sm_3),
            bottom = dimensionResource(id = R.dimen.padding_sm_3)
        ),
    ) {
        AppText(
            text = stringResource(id = R.string.configuration_category),
            modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_sm))
        )
        AppTextField(
            text = stringResource(id = R.string.configuration_category_name),
            modifier = Modifier.fillMaxWidth(),
            value = categoryName.value!!,
            onValueChange = { categoryVM.setCategoryName(it) },
        )
        Spacer(modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_sm)))
        MyCategoryTypeExposedDropdownMenuBox()
        AppRow(modifier = Modifier
            .align(Alignment.End)
            .padding(top = dimensionResource(id = R.dimen.padding_sm))
        ) {
            AppButton(
                onClick = {
                    categoryVM.setCategoryName("")
                    categoryVM.setCategoryType("")
                    configurationVM.canAddCategory()
                },
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.errorContainer)
            ) {
                AppText(
                    text = stringResource(id = R.string.cancel),
                    color = MaterialTheme.colorScheme.onErrorContainer
                )
            }
            Spacer(Modifier.size(dimensionResource(id = R.dimen.padding_xs)))
            AppButton(
                onClick = { categoryVM.canSaveCategory() },
                buttonContent = {
                    AppText(
                        text = stringResource(id = R.string.save),
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            )
            if (saveCategory.value!!) {
                categoryType = categoryTypeToEnumValue(type = categoryVM.categoryType.value!!)
                LaunchedEffect(key1 = 1) {
                    async {
                        categoryVM.saveCategory(categoryName.value!!, categoryType!!)
                        categoryVM.canSaveCategory()
                        configurationVM.canAddCategory()
                    }.await()
                }

            }
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