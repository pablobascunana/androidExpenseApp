package com.pbs.expenseApp.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.pbs.expenseApp.ExpenseApplication
import com.pbs.expenseApp.ui.screens.user.UserEditViewModel
import com.pbs.expenseApp.ui.screens.user.UserEntryViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            UserEditViewModel(
                this.createSavedStateHandle()
            )
        }
        initializer {
            UserEntryViewModel(expenseApplication().container.userRepository)
        }
    }
}
fun CreationExtras.expenseApplication(): ExpenseApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as ExpenseApplication)
