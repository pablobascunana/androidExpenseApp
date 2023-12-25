package com.pbs.expenseApp.ui.composables.addmonthlymovements

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.pbs.expenseApp.R
import com.pbs.expenseApp.ui.components.AppColumn

@Composable
fun MyAddExpenseModalBottomSheet(
    onClickNegative: () -> Unit,
    onClickPositive: () -> Unit,
) {

    AppColumn(
        modifier = Modifier.padding(
            start = dimensionResource(id = R.dimen.padding_sm_3),
            end = dimensionResource(id = R.dimen.padding_sm_3),
            bottom = dimensionResource(id = R.dimen.padding_sm_3)
        ),
    ) {

    }
}