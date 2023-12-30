package com.pbs.expenseApp.utils

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import android.widget.Toast
import com.pbs.expenseApp.R
import com.pbs.expenseApp.domain.model.CategoryType
import com.pbs.expenseApp.domain.model.MethodType
import java.util.UUID

object AppUtils {
    @SuppressLint("HardwareIds")
    @JvmStatic
    fun getAppId(context: Context): String =
        Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)

    @JvmStatic
    fun getUuid(): String {
        return UUID.randomUUID().toString()
    }

    @JvmStatic
    fun getDecimalPattern(): Regex {
        return Regex(pattern = "^\\d+\$")
    }

    @JvmStatic
    fun getString(context: Context, id: Int): String {
        return context.getString(id)
    }

    @JvmStatic
    fun categoryTypeToString(context: Context, type: String): String {
        return when (type) {
            CategoryType.INCOME.value -> getString(
                context = context, id = R.string.category_type_income
            )
            else -> getString(context = context, id = R.string.category_type_expense)
        }
    }

    @JvmStatic
    fun categoryTypesToString(
        context: Context, categoryTypes: Array<CategoryType>
    ): Array<CategoryType> {
        categoryTypes.forEach { category ->
            category.value = categoryTypeToString(context = context, type = category.value)
        }
        return categoryTypes
    }

    @JvmStatic
    fun categoryTypeToEnum(context: Context, category: String): CategoryType {
        return when (category) {
            getString(context = context, id = R.string.category_type_income) -> CategoryType.INCOME
            else -> CategoryType.EXPENSE
        }
    }

    @JvmStatic
    fun showToast(context: Context, textId: Int) {
        Toast.makeText(
            context, getString(context = context, id = textId), Toast.LENGTH_SHORT
        ).show()
    }

    @JvmStatic
    fun payMethodToString(context: Context, type: String): String {
        return when (type) {
            MethodType.CASH.value -> getString(
                context = context, id = R.string.pay_method_cash
            )
            MethodType.CREDIT.value -> getString(
                context = context, id = R.string.pay_method_credit
            )
            MethodType.DEBIT.value -> getString(
                context = context, id = R.string.pay_method_debit
            )
            MethodType.TRANSFER.value -> getString(
                context = context, id = R.string.pay_method_transfer
            )
            else -> getString(context = context, id = R.string.pay_method_bizum)
        }
    }
    @JvmStatic
    fun payMethodTypesToString(context: Context, methodTypes: Array<MethodType>
    ): Array<MethodType> {
        methodTypes.forEach { payMethod ->
            payMethod.value = payMethodToString(context = context, type = payMethod.value)
        }
        return methodTypes
    }
    @JvmStatic
    fun payMethodTypeToEnum(context: Context, payMethod: String): MethodType {
        return when (payMethod) {
            getString(context = context, id = R.string.pay_method_cash) -> MethodType.CASH
            getString(context = context, id = R.string.pay_method_credit) -> MethodType.CREDIT
            getString(context = context, id = R.string.pay_method_debit) -> MethodType.DEBIT
            getString(context = context, id = R.string.pay_method_transfer) -> MethodType.TRANSFER
            else -> MethodType.BIZUM
        }
    }
}
