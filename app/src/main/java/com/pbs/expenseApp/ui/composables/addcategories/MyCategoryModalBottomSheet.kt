package com.pbs.expenseApp.ui.composables.addcategories

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pbs.expenseApp.R
import com.pbs.expenseApp.ui.AppViewModelProvider
import com.pbs.expenseApp.ui.components.AppButton
import com.pbs.expenseApp.ui.components.AppColumn
import com.pbs.expenseApp.ui.components.AppExposedDropdownMenuBox
import com.pbs.expenseApp.ui.components.AppRow
import com.pbs.expenseApp.ui.components.AppText
import com.pbs.expenseApp.ui.components.AppTextField
import com.pbs.expenseApp.ui.viewmodels.CategoryViewModel
import com.pbs.expenseApp.ui.viewmodels.ExposedDropDownViewModel
import com.pbs.expenseApp.utils.AppUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyCategoryModalBottomSheet(
    text: String,
    onClickNegative: () -> Unit,
    onClickPositive: () -> Unit,
) {
    val context = LocalContext.current
    val categoryVM: CategoryViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val dropdownVM: ExposedDropDownViewModel = viewModel(factory = AppViewModelProvider.Factory)

    val categoryTypes = AppUtils.categoryTypesToString(
        context = context, categoryTypes = categoryVM.categoryTypes
    )

    AppColumn(
        modifier = Modifier.padding(
            start = dimensionResource(id = R.dimen.padding_sm_3),
            end = dimensionResource(id = R.dimen.padding_sm_3),
            bottom = dimensionResource(id = R.dimen.padding_sm_3)
        ),
    ) {
        AppText(
            text = text,
            modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_sm))
        )
        AppExposedDropdownMenuBox(
            text = stringResource(id = R.string.configuration_category_type)
        ) {
            categoryTypes.forEach { categoryType ->
                DropdownMenuItem(
                    text = { AppText(text = categoryType.value) },
                    onClick = {
                        dropdownVM.dropdownValue = categoryType.value
                        dropdownVM.dropdownExpanded = !dropdownVM.dropdownExpanded
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
        Spacer(modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_sm)))
        AppTextField(
            text = stringResource(id = R.string.configuration_category_name),
            modifier = Modifier.fillMaxWidth(),
            value = categoryVM.categoryName,
            onValueChange = { categoryVM.categoryName = it },
        )
        AppRow(modifier = Modifier
            .align(Alignment.End)
            .padding(top = dimensionResource(id = R.dimen.padding_sm))
        ) {
            AppButton(
                onClick = { onClickNegative() },
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.errorContainer)
            ) {
                AppText(
                    text = stringResource(id = R.string.cancel),
                    color = MaterialTheme.colorScheme.onErrorContainer
                )
            }
            Spacer(Modifier.size(dimensionResource(id = R.dimen.padding_xs)))
            AppButton(
                enabled = dropdownVM.dropdownValue.isNotEmpty() &&
                        categoryVM.categoryName.isNotEmpty(),
                onClick = { onClickPositive() },
                buttonContent = {
                    AppText(
                        text = stringResource(id = R.string.save),
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            )
        }
    }
}
