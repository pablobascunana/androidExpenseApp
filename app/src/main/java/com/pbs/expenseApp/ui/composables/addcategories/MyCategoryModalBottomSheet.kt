package com.pbs.expenseApp.ui.composables.addcategories

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pbs.expenseApp.R
import com.pbs.expenseApp.ui.AppViewModelProvider
import com.pbs.expenseApp.ui.components.AppButton
import com.pbs.expenseApp.ui.components.AppColumn
import com.pbs.expenseApp.ui.components.AppRow
import com.pbs.expenseApp.ui.components.AppText
import com.pbs.expenseApp.ui.components.AppTextField
import com.pbs.expenseApp.ui.viewmodels.CategoryViewModel

@Composable
fun MyConfigurationModalBottomSheet(
    text: String,
    onClickNegative: () -> Unit,
    onClickPositive: () -> Unit,
) {
    AppColumn(
        modifier = Modifier.padding(
            start = dimensionResource(id = R.dimen.padding_sm_3),
            end = dimensionResource(id = R.dimen.padding_sm_3),
            bottom = dimensionResource(id = R.dimen.padding_sm_3)
        ),
    ) {
        val categoryVM: CategoryViewModel = viewModel(factory = AppViewModelProvider.Factory)

        AppText(
            text = text,
            modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_sm))
        )
        MyCategoryTypeExposedDropdownMenuBox()
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
                enabled = categoryVM.categoryName.isNotEmpty() &&
                        categoryVM.categoryType.isNotEmpty(),
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
