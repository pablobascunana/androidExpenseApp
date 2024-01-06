package com.pbs.expenseApp.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale

object DateUtils {

    @JvmStatic
    fun getActualMonthAndYear(date: LocalDate): String {
        return DateTimeFormatter.ofPattern(
            "MMM yyyy", Locale("es", "ES")).format(date)
    }

    @JvmStatic
    fun getMonthsDifference(startDate: LocalDate, endDate: LocalDate): Long {
        return ChronoUnit.MONTHS.between(startDate, endDate)
    }
}