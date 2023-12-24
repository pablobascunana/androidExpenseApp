package com.pbs.expenseApp.ui.composables

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.pbs.expenseApp.R
import com.pbs.expenseApp.ui.components.AppText

@Composable
fun MyMonthlySavingText(monthlySavings: Int) {
    AppText(text = stringResource(id = R.string.add_expense_monthly_savings_1))
    Text(
        text = " $monthlySavings€ ",
        fontWeight = FontWeight.Bold
    )
    AppText(text = stringResource(id = R.string.add_expense_monthly_savings_2))
}
