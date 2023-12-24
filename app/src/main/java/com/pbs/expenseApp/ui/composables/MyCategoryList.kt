package com.pbs.expenseApp.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pbs.expenseApp.R
import com.pbs.expenseApp.domain.model.Category
import com.pbs.expenseApp.domain.model.CategoryType
import com.pbs.expenseApp.ui.AppViewModelProvider
import com.pbs.expenseApp.ui.components.AppCard
import com.pbs.expenseApp.ui.components.AppIcon
import com.pbs.expenseApp.ui.components.AppLazyColum
import com.pbs.expenseApp.ui.components.AppModalBottomSheet
import com.pbs.expenseApp.ui.components.AppRow
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
    AppLazyColum {
        items(categories) { category ->
            AppCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = dimensionResource(id = R.dimen.padding_xs))
                    .height(dimensionResource(R.dimen.card_height)),
                containerColor = getCategoryCardColor(category)
            ) {
                AppRow(modifier = Modifier
                    .fillMaxHeight()
                    .padding(
                        start = dimensionResource(id = R.dimen.padding_sm),
                        end = dimensionResource(id = R.dimen.padding_sm)
                    )
                ) {
                    /* AppIcon(
                        imageVector = Icons.Filled.Hd,
                        contentDescription = "description",
                        modifier = Modifier
                            .padding(end = dimensionResource(id = R.dimen.padding_sm))
                    ) */
                    AppText(
                        text = category.name,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    if (!category.isDefault) {
                        AppIcon(
                            imageVector = Icons.Outlined.Edit,
                            contentDescription = stringResource(id = R.string.edit),
                            modifier = Modifier
                                .padding(end = dimensionResource(id = R.dimen.padding_xs))
                                .clickable {
                                    categoryVM.categoryType = AppUtils.categoryTypeToString(
                                        context, category.type.value
                                    )
                                    categoryVM.categoryName = category.name
                                    categoryVM.categoryToEdit = category
                                    configurationVM.editCategory = !configurationVM.editCategory
                                }
                        )
                        AppIcon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = stringResource(id = R.string.delete),
                            modifier = Modifier.clickable {
                                configurationVM.editCategory = !configurationVM.editCategory
                            }
                        )
                    }
                }
            }
        }
    }
    if(configurationVM.editCategory) {
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
        categoryVM.categoryToEdit.name = categoryVM.categoryName
        categoryVM.categoryToEdit.type = AppUtils.categoryTypeToEnum(
            context = context, type = categoryVM.categoryType
        )
        LaunchedEffect(key1 = 1) {
            async {
                categoryVM.editCategory(categoryVM.categoryToEdit)
                resetInputs(categoryVM)
                categoryVM.canEditCategory = !categoryVM.canEditCategory
                configurationVM.editCategory = !configurationVM.editCategory
            }.await()
        }
    }
}

@Composable
private fun getCategoryCardColor(item: Category): Color {
    if (item.type == CategoryType.INCOME) {
        return MaterialTheme.colorScheme.tertiaryContainer
    }
    return MaterialTheme.colorScheme.errorContainer
}

