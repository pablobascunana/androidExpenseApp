package com.pbs.expenseApp.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.pbs.expenseApp.R


@Composable
fun AppAlertDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = {
            AppIcon(imageVector = icon, contentDescription = stringResource(id = R.string.delete))
        },
        title = { AppText(text = dialogTitle) },
        text = { AppText(text = dialogText) },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            AppTextButton(
                onClick = { onConfirmation() }
            ) {
                AppText(text = stringResource(id = R.string.confirm))
            }
        },
        dismissButton = {
            AppTextButton(
                onClick = { onDismissRequest() }
            ) {
                AppText(text = stringResource(id = R.string.cancel))
            }
        }
    )
}
