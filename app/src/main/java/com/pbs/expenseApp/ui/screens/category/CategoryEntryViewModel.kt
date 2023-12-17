package com.pbs.expenseApp.ui.screens.category

import android.content.Context
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import com.pbs.expenseApp.R
import java.util.UUID

class CategoryEntryViewModel(context: Context): ViewModel() {
    private val incomeType: String = "income"
    private val expenseType: String = "expense"

    val incomeDefaultCategories: List<Category> = listOf(
        Category(uuid = getUuid(), type = incomeType, name = getString(context = context, id = R.string.income_debts)),
        Category(uuid = getUuid(), type = incomeType, name = getString(context = context, id = R.string.income_deposit)),
        Category(uuid = getUuid(), type = incomeType, name = getString(context = context, id = R.string.income_salary)),
        Category(uuid = getUuid(), type = incomeType, name = getString(context = context, id = R.string.income_savings))
    )
    val expenseDefaultCategories: List<Category> = listOf(
        Category(uuid = getUuid(), type = expenseType, name = getString(context = context, id = R.string.expense_bills)),
        Category(uuid = getUuid(), type = expenseType, name = getString(context = context, id = R.string.expense_car)),
        Category(uuid = getUuid(), type = expenseType, name = getString(context = context, id = R.string.expense_clothes)),
        Category(uuid = getUuid(), type = expenseType, name = getString(context = context, id = R.string.expense_entertainment)),
        Category(uuid = getUuid(), type = expenseType, name = getString(context = context, id = R.string.expense_food)),
        Category(uuid = getUuid(), type = expenseType, name = getString(context = context, id = R.string.expense_health)),
        Category(uuid = getUuid(), type = expenseType, name = getString(context = context, id = R.string.expense_house)),
        Category(uuid = getUuid(), type = expenseType, name = getString(context = context, id = R.string.expense_pets)),
        Category(uuid = getUuid(), type = expenseType, name = getString(context = context, id = R.string.expense_presents)),
        Category(uuid = getUuid(), type = expenseType, name = getString(context = context, id = R.string.expense_restaurants)),
        Category(uuid = getUuid(), type = expenseType, name = getString(context = context, id = R.string.expense_sports)),
        Category(uuid = getUuid(), type = expenseType, name = getString(context = context, id = R.string.expense_transports))
    )
}

data class Category(
    val uuid: String,
    val type: String,
    val name: String
)

private fun getUuid(): String {
    return UUID.randomUUID().toString()
}

private fun getString(context: Context, id: Int): String {
    return context.getString(id)
}