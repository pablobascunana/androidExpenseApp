package com.pbs.expenseApp.ui.viewmodels

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pbs.expenseApp.R
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
    private val expenseRepository: ExpenseRepository,
    context: Context
): ViewModel() {

    private val appContext = context
    private var payMethodTypes = enumValues<MethodType>()

    var expenses by mutableStateOf(emptyList<Expense>())
    var addExpense by mutableStateOf(false)
    var descriptionValue by mutableStateOf("")
    var movementType by mutableStateOf(CategoryType.INCOME)
    var amount by mutableStateOf("")
    var canInsertExpense by mutableStateOf(false)
    var expandedPayMethodDropDown by mutableStateOf(false)
    var payMethodSelected by mutableStateOf("")

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

    private suspend fun getExpenses(categoryType: CategoryType) {
        viewModelScope.async {
             expenseRepository.getExpensesByCategoryType(categoryType = categoryType).collect {
                 response: List<Expense> -> expenses = response
            }
        }.await()
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

    fun getPayMethods(): Array<MethodType> {
        payMethodTypes.forEach { payMethod ->
            payMethod.value = payMethodToString(context = appContext, type = payMethod.value)
        }
        return payMethodTypes
    }

    private fun payMethodToString(context: Context, type: String): String {
        return when (type) {
            MethodType.CASH.value -> AppUtils.getString(
                context = context, id = R.string.pay_method_cash
            )
            MethodType.CREDIT.value -> AppUtils.getString(
                context = context, id = R.string.pay_method_credit
            )
            MethodType.DEBIT.value -> AppUtils.getString(
                context = context, id = R.string.pay_method_debit
            )
            MethodType.TRANSFER.value -> AppUtils.getString(
                context = context, id = R.string.pay_method_transfer
            )
            else -> AppUtils.getString(context = context, id = R.string.pay_method_bizum)
        }
    }

    fun payMethodTypeToEnum(): MethodType {
        return when (payMethodSelected) {
            AppUtils.getString(context = appContext, id = R.string.pay_method_cash) -> MethodType.CASH
            AppUtils.getString(context = appContext, id = R.string.pay_method_credit) -> MethodType.CREDIT
            AppUtils.getString(context = appContext, id = R.string.pay_method_debit) -> MethodType.DEBIT
            AppUtils.getString(context = appContext, id = R.string.pay_method_transfer) -> MethodType.TRANSFER
            else -> MethodType.BIZUM
        }
    }

    suspend fun getExpenseList() {
        if (movementType == CategoryType.INCOME) {
            getExpenses(categoryType = CategoryType.INCOME)
        }
        getExpenses(categoryType = CategoryType.EXPENSE)
    }

    fun getExpenseText(): String {
        if (movementType == CategoryType.INCOME) {
            return AppUtils.getString(context = appContext, id = R.string.add_monthly_incomes)
        }
        return AppUtils.getString(context = appContext, id = R.string.add_monthly_expenses)
    }
}
