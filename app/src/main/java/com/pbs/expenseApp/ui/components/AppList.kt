package com.pbs.expenseApp.ui.components

import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.pbs.expenseApp.domain.model.Category
import com.pbs.expenseApp.domain.model.Expense

@JvmName("AppCategoryList")
@Composable
fun AppList(
    items: List<Category>,
    onEdit: (item: Category) -> Unit,
    onDelete: (item: Category) -> Unit,
) {
    AppLazyColum {
        items(items) { item ->
            AppListCard(item = item, onEdit = onEdit, onDelete = onDelete)
        }
    }
}

@JvmName("AppExpenseList")
@Composable
fun AppList(
    items: List<Expense>,
    onEdit: (item: Expense) -> Unit,
    onDelete: (item: Expense) -> Unit
) {
    AppLazyColum {
        items(items) { item ->
            AppListCard(item = item, onEdit = onEdit, onDelete = onDelete)
        }
    }
}
