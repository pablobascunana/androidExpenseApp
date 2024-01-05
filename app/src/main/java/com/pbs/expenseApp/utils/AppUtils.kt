package com.pbs.expenseApp.utils

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import android.widget.Toast
import com.pbs.expenseApp.R
import com.pbs.expenseApp.domain.model.Category
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
    fun getDefaultCategories(context: Context): List<Category> {
        return listOf(
            Category(uuid = getUuid(), userUuid = getAppId(context), type = CategoryType.INCOME, name = getString(context = context, id = R.string.income_salary)),
            Category(uuid = getUuid(), userUuid = getAppId(context), type = CategoryType.INCOME, name = getString(context = context, id = R.string.income_deposit)),
            Category(uuid = getUuid(), userUuid = getAppId(context), type = CategoryType.INCOME, name = getString(context = context, id = R.string.income_savings)),
            Category(uuid = getUuid(), userUuid = getAppId(context), type = CategoryType.INCOME, name = getString(context = context, id = R.string.income_debts)),
            Category(uuid = getUuid(), userUuid = getAppId(context), type = CategoryType.EXPENSE, name = getString(context = context, id = R.string.expense_bills)),
            Category(uuid = getUuid(), userUuid = getAppId(context), type = CategoryType.EXPENSE, name = getString(context = context, id = R.string.expense_car)),
            Category(uuid = getUuid(), userUuid = getAppId(context), type = CategoryType.EXPENSE, name = getString(context = context, id = R.string.expense_clothes)),
            Category(uuid = getUuid(), userUuid = getAppId(context), type = CategoryType.EXPENSE, name = getString(context = context, id = R.string.expense_entertainment)),
            Category(uuid = getUuid(), userUuid = getAppId(context), type = CategoryType.EXPENSE, name = getString(context = context, id = R.string.expense_food)),
            Category(uuid = getUuid(), userUuid = getAppId(context), type = CategoryType.EXPENSE, name = getString(context = context, id = R.string.expense_health)),
            Category(uuid = getUuid(), userUuid = getAppId(context), type = CategoryType.EXPENSE, name = getString(context = context, id = R.string.expense_house)),
            Category(uuid = getUuid(), userUuid = getAppId(context), type = CategoryType.EXPENSE, name = getString(context = context, id = R.string.expense_pets)),
            Category(uuid = getUuid(), userUuid = getAppId(context), type = CategoryType.EXPENSE, name = getString(context = context, id = R.string.expense_presents)),
            Category(uuid = getUuid(), userUuid = getAppId(context), type = CategoryType.EXPENSE, name = getString(context = context, id = R.string.expense_restaurants)),
            Category(uuid = getUuid(), userUuid = getAppId(context), type = CategoryType.EXPENSE, name = getString(context = context, id = R.string.expense_sports)),
            Category(uuid = getUuid(), userUuid = getAppId(context), type = CategoryType.EXPENSE, name = getString(context = context, id = R.string.expense_transports))
        )
    }

    @JvmStatic
    fun getIntegerPattern(): Regex {
        return Regex(pattern = "^\\d+\$")
    }
    @JvmStatic
    fun getDecimalPattern(): Regex {
        return Regex(pattern = "\\d+(\\.\\d*)?|\\.\\d+")
    }

    @JvmStatic
    fun getString(context: Context, id: Int): String {
        return context.getString(id)
    }

    @JvmStatic
    fun showToast(context: Context, textId: Int) {
        Toast.makeText(
            context, getString(context = context, id = textId), Toast.LENGTH_SHORT
        ).show()
    }
}
