package com.pbs.expenseApp.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.pbs.expenseApp.domain.model.CategoryType
import com.pbs.expenseApp.domain.model.Expense

class ExpenseViewModel: ViewModel() {

    var addExpense by mutableStateOf(false)
    var incomeList by mutableStateOf(emptyList<Expense>())
    var expenseList by mutableStateOf(emptyList<Expense>())
    var descriptionValue by mutableStateOf("")
    var movementType by mutableStateOf(CategoryType.INCOME)
    var amountValue by mutableStateOf("")
    var categoryName by mutableStateOf("")
}