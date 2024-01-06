package com.pbs.expenseApp.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.pbs.expenseApp.utils.DateUtils
import java.time.LocalDate

class HorizontalPagerViewModel: ViewModel() {

    private var pagerDate: LocalDate by mutableStateOf(LocalDate.now())
    var formattedPagerDate: String by mutableStateOf(DateUtils.getActualMonthAndYear(pagerDate))
    var pageCount: Int by mutableIntStateOf(0)

    fun calculatePageCount(userDate: LocalDate) {
        pageCount = DateUtils.getMonthsDifference(userDate, pagerDate).toInt() + 1
    }

    fun setPagerDate(pageCount: Long) {
        val actualDate = DateUtils.addMonths(date = pagerDate, monthsToAdd = pageCount)
        formattedPagerDate = DateUtils.getActualMonthAndYear(actualDate)
    }
}