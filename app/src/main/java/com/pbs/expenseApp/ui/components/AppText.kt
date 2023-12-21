package com.pbs.expenseApp.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import com.pbs.expenseApp.R
import com.pbs.expenseApp.ui.composables.fontDimensionResource


// TODO REFACTOR AppText have to receive as parameter text string not id int
@Composable
fun AppText(
    modifier: Modifier = Modifier,
    fontSize: TextUnit = fontDimensionResource(id = R.dimen.font_sm),
    text: String,
    color: Color = MaterialTheme.colorScheme.primary
) {
    Text(
        text = text,
        color = color,
        fontSize = fontSize,
        modifier = modifier
    )
}
