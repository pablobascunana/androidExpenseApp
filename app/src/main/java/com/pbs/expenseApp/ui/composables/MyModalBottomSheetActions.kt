package com.pbs.expenseApp.ui.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.pbs.expenseApp.R
import com.pbs.expenseApp.ui.components.AppButton
import com.pbs.expenseApp.ui.components.AppText

@Composable
fun MyModalBottomSheetActions(
    enabled: Boolean = true,
    onClickNegative: () -> Unit,
    onClickPositive: () -> Unit
) {
    AppButton(
        onClick = { onClickNegative() },
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.errorContainer)
    ) {
        AppText(
            text = stringResource(id = R.string.cancel),
            color = MaterialTheme.colorScheme.onErrorContainer
        )
    }
    Spacer(Modifier.size(dimensionResource(id = R.dimen.padding_xs)))
    AppButton(
        enabled = enabled,
        onClick = { onClickPositive() },
        buttonContent = {
            AppText(
                text = stringResource(id = R.string.save),
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    )
}
