package com.pbs.expenseApp.ui.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.pbs.expenseApp.utils.DateUtils
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class HorizontalPagerViewModel: ViewModel() {

    private var pagerDate: LocalDate by mutableStateOf(LocalDate.now())
    val formattedPagerDate: String by derivedStateOf { DateUtils.getActualMonthAndYear(pagerDate) }
}