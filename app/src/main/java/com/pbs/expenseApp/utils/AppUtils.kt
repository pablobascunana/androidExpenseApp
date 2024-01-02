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
    fun showToast(context: Context, textId: Int) {
        Toast.makeText(
            context, getString(context = context, id = textId), Toast.LENGTH_SHORT
        ).show()
    }
}
