package com.pbs.expenseApp.ui.viewmodels

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.pbs.expenseApp.utils.DateUtils
import java.time.LocalDate

class HorizontalPagerViewModel: ViewModel() {

    private var pagerDate: LocalDate by mutableStateOf(LocalDate.now())
    val formattedPagerDate: String by derivedStateOf { DateUtils.getActualMonthAndYear(pagerDate) }
}