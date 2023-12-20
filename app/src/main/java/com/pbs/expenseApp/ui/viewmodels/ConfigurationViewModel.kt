package com.pbs.expenseApp.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ConfigurationViewModel: ViewModel() {

    private val _monthlySavingsInputValue = MutableLiveData("")
    val monthlySavingsInputValue: LiveData<String>
        get() = _monthlySavingsInputValue

    private val _editMonthlySavings = MutableLiveData(false)
    val editMonthlySavings: LiveData<Boolean>
        get() = _editMonthlySavings

    private val _decimalPattern = MutableLiveData(Regex(pattern = "^\\d+\$"))
    val decimalPattern: LiveData<Regex>
        get() = _decimalPattern

    fun setMonthlySavingsInputValue(value: String = "") {
        _monthlySavingsInputValue.value = value
    }

    fun canEditMonthlySavings() {
        _editMonthlySavings.value = !_editMonthlySavings.value!!
    }
}
