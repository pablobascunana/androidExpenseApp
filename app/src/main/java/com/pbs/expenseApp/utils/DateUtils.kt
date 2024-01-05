package com.pbs.expenseApp.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

object DateUtils {

    @RequiresApi(Build.VERSION_CODES.O)
    @JvmStatic
    fun getActualMonthAndYear(date: LocalDate): String {
        return DateTimeFormatter.ofPattern(
            "MMM yyyy", Locale("es", "ES")).format(date)
    }
}