package com.pbs.expenseApp.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pbs.expenseApp.domain.model.Category
import com.pbs.expenseApp.domain.model.CategoryType
import com.pbs.expenseApp.domain.model.Expense
import com.pbs.expenseApp.domain.model.MethodType
import com.pbs.expenseApp.domain.repository.ExpenseRepository
import com.pbs.expenseApp.utils.AppUtils
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.Date

class ExpenseViewModel(
    private val expenseRepository: ExpenseRepository
): ViewModel() {

    var addExpense by mutableStateOf(false)
    var incomeList by mutableStateOf(emptyList<Expense>())
    var expenseList by mutableStateOf(emptyList<Expense>())
    var descriptionValue by mutableStateOf("")
    var movementType by mutableStateOf(CategoryType.INCOME)
    var amountValue by mutableStateOf(0)
    var payMethod by mutableStateOf(MethodType.CREDIT)
    var canInsertExpense by mutableStateOf(false)

    suspend fun insert(category: Category, amount: Int, description: String, payMethod: MethodType) {
        val expense = toExpense(
            category = category, amount = amount, description = description, payMethod = payMethod
        )
        viewModelScope.launch {
            viewModelScope.async {
                expenseRepository.insert(expense = expense)
            }.await()
        }
    }

    private fun toExpense(
        category: Category, amount: Int, description: String, payMethod: MethodType
    ): Expense {
        return Expense(
            uuid = AppUtils.getUuid(),
            userUuid = category.userUuid,
            categoryUuid = category.uuid,
            amount = amount,
            date = Date(),
            payMethod = payMethod,
            description = description
        )
    }
}
