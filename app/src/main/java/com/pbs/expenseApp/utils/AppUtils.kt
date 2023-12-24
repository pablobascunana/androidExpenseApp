package com.pbs.expenseApp.utils

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import androidx.compose.ui.res.stringResource
import com.pbs.expenseApp.R
import com.pbs.expenseApp.domain.model.CategoryType
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
        context: Context, types: Array<CategoryType>
    ): Array<CategoryType> {
        types.forEach { category ->
            category.value = categoryTypeToString(context = context, type = category.value)
        }
        return types
    }
    @JvmStatic
    fun categoryTypeToEnum(context: Context, type: String): CategoryType {
        return when (type) {
            getString(
                context = context, id = R.string.category_type_income) -> CategoryType.INCOME
            else -> CategoryType.EXPENSE
        }
    }

}
