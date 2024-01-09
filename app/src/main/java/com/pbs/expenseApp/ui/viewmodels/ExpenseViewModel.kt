package com.pbs.expenseApp.ui.viewmodels

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.pbs.expenseApp.R
import com.pbs.expenseApp.domain.model.Category
import com.pbs.expenseApp.domain.model.CategoryType
import com.pbs.expenseApp.domain.model.Expense
import com.pbs.expenseApp.domain.model.MethodType
import com.pbs.expenseApp.domain.repository.ExpenseRepository
import com.pbs.expenseApp.utils.AppUtils

class ExpenseViewModel(
    private val expenseRepository: ExpenseRepository,
    context: Context
): ViewModel() {

    private val appContext = context
    private var payMethodTypes = enumValues<MethodType>()

    var expenses by mutableStateOf(emptyList<Expense>())
    var monthlyExpenses by mutableStateOf(emptyList<Expense>())
    var addExpense by mutableStateOf(false)
    var descriptionValue by mutableStateOf("")
    var movementType by mutableStateOf(CategoryType.INCOME)
    var amount by mutableStateOf("")
    var canInsertExpense by mutableStateOf(false)
    var expandedPayMethodDropDown by mutableStateOf(false)
    var payMethodSelected by mutableStateOf("")
    var canEdit by mutableStateOf(false)
    var confirmEdit by mutableStateOf(false)
    var canDelete by mutableStateOf(false)
    var confirmDelete by mutableStateOf(false)
    var expenseSelected by mutableStateOf(Expense())

    suspend fun insert(category: Category, amount: Float, description: String, payMethod: MethodType) {
        // TODO work with expense and remove toExpense function
        val expense = toExpense(
            category = category, amount = amount, description = description, payMethod = payMethod
        )
        expenseRepository.insert(expense = expense)
    }
    private suspend fun getExpensesByCategoryType(categoryType: CategoryType) {
         expenseRepository.getExpensesByCategoryType(categoryType = categoryType).collect {
             response: List<Expense> -> expenses = response
         }
    }
    suspend fun getMonthlyExpenses() {
        expenseRepository.getMonthlyExpenses().collect {
                response: List<Expense> -> monthlyExpenses = response
        }
    }
    suspend fun update(category: Category, uuid: String, amount: Float, description: String, payMethod: MethodType) {
        // TODO work with expense and remove toExpense function
        val expense = toExpense(
            category = category, uuid = uuid, amount = amount,
            description = description, payMethod = payMethod
        )
        expenseRepository.update(expense = expense)
    }
    suspend fun delete(expense: Expense) {
        expenseRepository.delete(expense)
    }

    private fun toExpense(
        category: Category, uuid: String = AppUtils.getUuid(), amount: Float,
        description: String, payMethod: MethodType
    ): Expense {
        return Expense(
            uuid = uuid,
            userUuid = category.userUuid,
            categoryUuid = category.uuid,
            amount = amount,
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
            getExpensesByCategoryType(categoryType = CategoryType.INCOME)
        }
        getExpensesByCategoryType(categoryType = CategoryType.EXPENSE)
    }
    fun getCreateExpenseText(): String {
        if (movementType == CategoryType.INCOME) {
            return AppUtils.getString(context = appContext, id = R.string.add_monthly_income)
        }
        return AppUtils.getString(context = appContext, id = R.string.add_monthly_expense)
    }
    fun getEditExpenseText(): String {
        if (movementType == CategoryType.INCOME) {
            return AppUtils.getString(context = appContext, id = R.string.edit_monthly_income)
        }
        return AppUtils.getString(context = appContext, id = R.string.edit_monthly_expense)
    }
}
