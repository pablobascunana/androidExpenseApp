package com.pbs.expenseApp.ui.viewmodels

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pbs.expenseApp.R

class ConfigurationViewModel: ViewModel() {

    private val _monthlySavingsInputValue = MutableLiveData("")
    val monthlySavingsInputValue: LiveData<String>
        get() = _monthlySavingsInputValue

    private val _editMonthlySavings = MutableLiveData(false)
    val editMonthlySavings: LiveData<Boolean>
        get() = _editMonthlySavings

    private val _cardItems = MutableLiveData(
        listOf(
            // CardItem(title = R.string.configuration_card_category),
            CardItem(title = R.string.configuration_card_savings),
            CardItem(title = R.string.configuration_card_expenses)
        )
    )
    val cardItems: LiveData<List<CardItem>>
        get() = _cardItems

    fun setMonthlySavingsInputValue(value: String = "") {
        _monthlySavingsInputValue.value = value
    }

    fun canEditMonthlySavings() {
        _editMonthlySavings.value = !_editMonthlySavings.value!!
    }
}

data class CardItem(
    val title: Int = 0,
    var containerColor: Color? = null
)