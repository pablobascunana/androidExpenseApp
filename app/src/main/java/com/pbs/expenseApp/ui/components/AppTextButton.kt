package com.pbs.expenseApp.ui.components

import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun AppTextButton(
    onClick: () -> Unit,
    textButtonContent: @Composable () -> Unit
) {
    TextButton(
        onClick = { onClick() }
    ) {
        textButtonContent()
    }
}