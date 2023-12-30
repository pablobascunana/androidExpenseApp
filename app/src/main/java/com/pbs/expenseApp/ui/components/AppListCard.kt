package com.pbs.expenseApp.ui.components

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import com.pbs.expenseApp.R
import com.pbs.expenseApp.domain.model.Category
import com.pbs.expenseApp.domain.model.CategoryType
import com.pbs.expenseApp.domain.model.Expense
import com.pbs.expenseApp.ui.composables.addcategories.CardContentCategory
import com.pbs.expenseApp.ui.composables.addmonthlymovements.CardContentIncome

@JvmName("AppCategoryListCard")
@Composable
fun AppListCard(
    item: Category,
    onEdit: (item: Category) -> Unit,
    onDelete: (item: Category) -> Unit,
) {
    AppCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = dimensionResource(id = R.dimen.padding_xs))
            .height(dimensionResource(R.dimen.card_height_sm)),
        containerColor = getCategoryCardColor(item)
    ) {
        CardContentCategory(item = item, onEdit = onEdit, onDelete = onDelete)
    }
}

@Composable
private fun getCategoryCardColor(item: Category): Color {
    if (item.type == CategoryType.INCOME) {
        return MaterialTheme.colorScheme.tertiaryContainer
    }
    return MaterialTheme.colorScheme.errorContainer
}

@JvmName("AppExpenseListCard")
@Composable
fun AppListCard(
    item: Expense,
    onEdit: (item: Expense) -> Unit,
    onDelete: (item: Expense) -> Unit,
) {
    AppCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = dimensionResource(id = R.dimen.padding_xs))
            .height(dimensionResource(R.dimen.card_height_sm)),
        containerColor = MaterialTheme.colorScheme.secondaryContainer
    ) {
        AppRow(
            modifier = Modifier
                .fillMaxHeight()
                .padding(
                    start = dimensionResource(id = R.dimen.padding_sm),
                    end = dimensionResource(id = R.dimen.padding_sm)
                )
        ) {
            CardContentIncome(item = item, onEdit = onEdit, onDelete = onDelete)
        }
    }
}
