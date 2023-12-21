package com.pbs.expenseApp.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource

@Composable
fun AppText(
    id: Int,
    color: Color = MaterialTheme.colorScheme.primary
) {
    Text(
        text = stringResource(id = id),
        color = color
    )
}
