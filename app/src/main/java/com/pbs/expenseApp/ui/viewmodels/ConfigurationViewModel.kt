package com.pbs.expenseApp.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.pbs.expenseApp.R

class ConfigurationViewModel: ViewModel() {

    var monthlySavingsInputValue by mutableStateOf("")
    var editMonthlySavings by mutableStateOf(false)
    var addCategory by mutableStateOf(false)
    val cardItems by mutableStateOf(listOf(
        CardItem(title = R.string.configuration_card_savings),
        CardItem(title = R.string.configuration_card_expenses)
    ))
}

data class CardItem(
    val title: Int = 0,
    var containerColor: Color? = null
)