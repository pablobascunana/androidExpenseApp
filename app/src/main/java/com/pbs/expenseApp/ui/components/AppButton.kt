package com.pbs.expenseApp.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun AppButton(
    enabled: Boolean = true,
    onClick: () -> Unit,
    colors: ButtonColors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondaryContainer),
    buttonContent: @Composable () -> Unit
) {
    Button(
        enabled = enabled,
        onClick = { onClick() },
        colors = colors,
    ) {
        buttonContent()
    }
}
