package com.pbs.expenseApp.ui.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.pbs.expenseApp.R
import com.pbs.expenseApp.database.entities.CategoryType
import com.pbs.expenseApp.ui.components.AppButton
import com.pbs.expenseApp.ui.components.AppColumn
import com.pbs.expenseApp.ui.components.AppRow
import com.pbs.expenseApp.ui.components.AppText
import com.pbs.expenseApp.ui.components.AppTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAddCategoryModalBottomSheet() {
    val categoryTypes = enumValues<CategoryType>()

    categoryTypes.forEach { category ->
        when (category.value) {
            CategoryType.INCOME.value ->
                category.value = stringResource(id = R.string.category_type_income)
            else -> category.value = stringResource(id = R.string.category_type_expense)
        }
    }

    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf("") }
    var categoryText by remember { mutableStateOf("") }

    AppColumn(
        modifier = Modifier.padding(
            start = dimensionResource(id = R.dimen.padding_sm_3),
            end = dimensionResource(id = R.dimen.padding_sm_3),
            bottom = dimensionResource(id = R.dimen.padding_sm_3)
        ),
    ) {
        AppTextField(
            text = stringResource(id = R.string.configuration_category_name),
            modifier = Modifier.fillMaxWidth(),
            value = categoryText,
            onValueChange = { categoryText = it },
        )
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
        ) {
            AppTextField(
                text = stringResource(id = R.string.configuration_category_type),
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                value = selectedOptionText,
                onValueChange = { selectedOptionText = it },
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                categoryTypes.forEach { categoryType ->
                    DropdownMenuItem(
                        text = { AppText(text = categoryType.value) },
                        onClick = {
                            selectedOptionText = categoryType.value
                            expanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }

        AppRow(modifier = Modifier
            .align(Alignment.End)
            .padding(top = dimensionResource(id = R.dimen.padding_sm))
        ) {
            AppButton(
                onClick = {},
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.errorContainer)
            ) {
                AppText(
                    text = stringResource(id = R.string.cancel),
                    color = MaterialTheme.colorScheme.onErrorContainer
                )
            }
            Spacer(Modifier.size(dimensionResource(id = R.dimen.padding_xs)))
            AppButton(
                onClick = {},
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
