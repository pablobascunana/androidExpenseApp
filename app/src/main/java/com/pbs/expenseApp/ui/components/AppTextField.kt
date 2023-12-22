package com.pbs.expenseApp.ui.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun AppTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    text: String,
    readOnly: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    trailingIcon: @Composable () -> Unit = {},
    onValueChange: (String) -> Unit) {
    TextField(
        modifier = modifier,
        value = value,
        readOnly = readOnly,
        onValueChange = onValueChange,
        label = { AppText(text = text) },
        trailingIcon = trailingIcon,
        keyboardOptions = keyboardOptions
    )
}
