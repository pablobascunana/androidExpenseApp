package com.pbs.expenseApp.ui.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.pbs.expenseApp.R
import com.pbs.expenseApp.domain.model.Expense
import com.pbs.expenseApp.ui.components.AppText

@Composable
fun MyIncomeList(
    navHostController: NavHostController
) {
    AppText(
        text = stringResource(id = R.string.add_incomes),
        fontSize = fontDimensionResource(id = R.dimen.font_md),
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_sm))
    )
    val incomes = emptyList<List<Expense>>()
    /* AppList(
        incomes,
        onEdit = {  },
        onDelete = {  }
    ) */
}
