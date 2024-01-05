package com.pbs.expenseApp.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.pbs.expenseApp.ExpenseApplication
import com.pbs.expenseApp.ui.viewmodels.CategoryViewModel
import com.pbs.expenseApp.ui.viewmodels.ConfigurationViewModel
import com.pbs.expenseApp.ui.viewmodels.HorizontalPagerViewModel
import com.pbs.expenseApp.ui.viewmodels.ExpenseViewModel
import com.pbs.expenseApp.ui.viewmodels.ExposedDropDownViewModel
import com.pbs.expenseApp.ui.viewmodels.UserViewModel

object AppViewModelProvider {
    @RequiresApi(Build.VERSION_CODES.O)
    val Factory = viewModelFactory {
        initializer {
            UserViewModel(expenseApplication().container.userRepository)
        }
        initializer {
            CategoryViewModel(
                expenseApplication().container.categoryRepository,
                expenseApplication().baseContext
            )
        }
        initializer {
            ConfigurationViewModel()
        }
        initializer {
            ExposedDropDownViewModel()
        }
        initializer {
            ExpenseViewModel(
                expenseApplication().container.expenseRepository,
                expenseApplication().baseContext
            )
        }
        initializer {
            HorizontalPagerViewModel()
        }
    }
}
fun CreationExtras.expenseApplication(): ExpenseApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as ExpenseApplication)
