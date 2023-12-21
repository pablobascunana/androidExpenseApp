package com.pbs.expenseApp.utils

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import java.util.UUID

object AppUtils {
    @SuppressLint("HardwareIds")
    @JvmStatic
    fun appId(context: Context): String =
        Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)

    @JvmStatic
    fun getUuid(): String {
        return UUID.randomUUID().toString()
    }
}
