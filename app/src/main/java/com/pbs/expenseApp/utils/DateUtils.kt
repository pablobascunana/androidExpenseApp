package com.pbs.expenseApp.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

object DateUtils {

    @JvmStatic
    fun getActualMonthAndYear(date: LocalDate): String {
        return DateTimeFormatter.ofPattern(
            "MMM yyyy", Locale("es", "ES")).format(date)
    }
}