package com.pbs.expenseApp.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.pbs.expenseApp.ExpenseApplication
import com.pbs.expenseApp.ui.screens.AppViewModel
import com.pbs.expenseApp.ui.screens.category.CategoryEntryViewModel
import com.pbs.expenseApp.ui.viewmodels.ConfigurationViewModel
import com.pbs.expenseApp.ui.viewmodels.UserViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            UserViewModel(expenseApplication().container.userRepository)
        }
        initializer {
            CategoryEntryViewModel(
                expenseApplication().container.categoryRepository,
                expenseApplication().baseContext
            )
        }
        initializer {
            AppViewModel(expenseApplication().baseContext)
        }
        initializer {
            ConfigurationViewModel()
        }
    }
}
fun CreationExtras.expenseApplication(): ExpenseApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as ExpenseApplication)
