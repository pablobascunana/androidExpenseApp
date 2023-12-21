package com.pbs.expenseApp.ui.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.LiveData
import com.pbs.expenseApp.R
import com.pbs.expenseApp.database.entities.Category
import com.pbs.expenseApp.database.entities.CategoryType
import com.pbs.expenseApp.ui.components.AppCard
import com.pbs.expenseApp.ui.components.AppLazyColum
import com.pbs.expenseApp.ui.components.AppRow
import com.pbs.expenseApp.ui.components.AppText


@Composable
fun MyCategoryList(categories: List<Category>) {
    AppText(text = stringResource(id = R.string.configuration_category_title),
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
                    .fillMaxSize()
                    .padding(
                        start = dimensionResource(id = R.dimen.padding_sm),
                        end = dimensionResource(id = R.dimen.padding_sm)
                    )
                ) {
                    AppText(text = category.name)
                }
            }
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