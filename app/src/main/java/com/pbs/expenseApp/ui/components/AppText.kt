package com.pbs.expenseApp.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

@Composable
fun AppText(id: Int) {
    Text(
        text = stringResource(id = id),
    )
}
