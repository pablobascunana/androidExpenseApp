package com.pbs.expenseApp.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ExpenseViewModel: ViewModel() {

    var addExpense by mutableStateOf(false)
}