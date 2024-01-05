package com.pbs.expenseApp.ui.composables.addmonthlymovements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.pbs.expenseApp.R
import com.pbs.expenseApp.domain.model.Expense
import com.pbs.expenseApp.ui.components.AppIcon
import com.pbs.expenseApp.ui.components.AppRow
import com.pbs.expenseApp.ui.components.AppText

@Composable
fun CardContentIncome(
    item: Expense,
    onEdit: (item: Expense) -> Unit,
    onDelete: (item: Expense) -> Unit,
) {
    AppRow(
        modifier = Modifier
            .fillMaxHeight()
            .padding(
                start = dimensionResource(id = R.dimen.padding_sm),
                end = dimensionResource(id = R.dimen.padding_sm)
            )
    ) {
        AppText(text = "${item.description} - ${item.amount}€")
        Spacer(modifier = Modifier.weight(1f))
        CardActionsIncome(item, onEdit = onEdit, onDelete = onDelete)
    }
}
@Composable
fun CardActionsIncome(
    item: Expense,
    onEdit: (item: Expense) -> Unit,
    onDelete: (item: Expense) -> Unit
) {
    AppIcon(
        imageVector = Icons.Outlined.Edit,
        contentDescription = stringResource(id = R.string.edit),
        modifier = Modifier
            .padding(end = dimensionResource(id = R.dimen.padding_xs))
            .clickable { onEdit(item) }
    )
    AppIcon(
        imageVector = Icons.Outlined.Delete,
        contentDescription = stringResource(id = R.string.delete),
        modifier = Modifier.clickable { onDelete(item) }
    )
}
