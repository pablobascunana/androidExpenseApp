package com.pbs.expenseApp.ui.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pbs.expenseApp.R
import com.pbs.expenseApp.ui.AppViewModelProvider
import com.pbs.expenseApp.ui.components.AppIcon
import com.pbs.expenseApp.ui.components.AppModalBottomSheet
import com.pbs.expenseApp.ui.viewmodels.ConfigurationViewModel

@Composable
fun MyAddCategoryFab() {
    val configurationMV: ConfigurationViewModel = viewModel(factory = AppViewModelProvider.Factory)

    val canAddCategory = configurationMV.addCategory.observeAsState()

    FloatingActionButton(
        onClick = {
            configurationMV.canAddCategory()
        },
    ) {
        AppIcon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(id = R.string.configuration_add_category)
        )
    }

    if(canAddCategory.value!!) {
        AppModalBottomSheet(onDismissRequest = { configurationMV.canAddCategory() }
        ) {
            MyAddCategoryModalBottomSheet()
        }
    }
}
