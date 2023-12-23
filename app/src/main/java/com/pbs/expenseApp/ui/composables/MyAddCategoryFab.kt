package com.pbs.expenseApp.ui.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pbs.expenseApp.R
import com.pbs.expenseApp.ui.AppViewModelProvider
import com.pbs.expenseApp.ui.components.AppIcon
import com.pbs.expenseApp.ui.components.AppModalBottomSheet
import com.pbs.expenseApp.ui.viewmodels.ConfigurationViewModel

@Composable
fun MyAddCategoryFab() {
    val configurationVM: ConfigurationViewModel = viewModel(factory = AppViewModelProvider.Factory)

    FloatingActionButton(
        onClick = {
            configurationVM.addCategory = !configurationVM.addCategory
        },
    ) {
        AppIcon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(id = R.string.configuration_add_category)
        )
    }

    if(configurationVM.addCategory) {
        AppModalBottomSheet(onDismissRequest = {
            configurationVM.addCategory = !configurationVM.addCategory
        }
        ) {
            MyAddCategoryModalBottomSheet()
        }
    }
}
