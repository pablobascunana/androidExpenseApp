package com.pbs.expenseApp.ui.composables

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

@Composable
fun MyText(id: Int) {
    Text(
        text = stringResource(id = id)
    )
}
