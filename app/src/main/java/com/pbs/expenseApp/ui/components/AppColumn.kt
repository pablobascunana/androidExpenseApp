package com.pbs.expenseApp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AppColumn(
    modifier: Modifier = Modifier,
    columnContent: @Composable ColumnScope.() -> Unit
) {
    Column(modifier = modifier) {
        columnContent()
    }
}