package com.pbs.expenseApp.ui.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable

@Composable
fun AppLazyColum(
    lazyColumnContent: LazyListScope.() -> Unit
) {
    LazyColumn {
        lazyColumnContent()
    }
}
