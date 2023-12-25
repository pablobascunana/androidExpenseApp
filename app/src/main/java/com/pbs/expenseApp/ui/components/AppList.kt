package com.pbs.expenseApp.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.pbs.expenseApp.domain.model.Category
import com.pbs.expenseApp.domain.model.Expense

@Composable
fun AppList(
    items: List<Category>,
    listContent: @Composable RowScope.(item: Category) -> Unit
) {
    AppLazyColum {
        items(items) { item ->
            AppListCard(item) {
                listContent(item)
            }
        }
    }
}

@Composable
fun AppList(
    items: List<Expense>,
    listContent: @Composable RowScope.(item: Expense) -> Unit
) {
    AppLazyColum {
        items(items) { item ->
            AppListCard {
                listContent(item)
            }
        }
    }
}
