package com.pbs.expenseApp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pbs.expenseApp.R
import com.pbs.expenseApp.ui.AppViewModelProvider
import com.pbs.expenseApp.ui.components.AppCard
import com.pbs.expenseApp.ui.components.AppColumn
import com.pbs.expenseApp.ui.components.AppIcon
import com.pbs.expenseApp.ui.components.AppRow
import com.pbs.expenseApp.ui.composables.MyMonthlySavingModalBottomSheet
import com.pbs.expenseApp.ui.composables.MyMonthlySavingText
import com.pbs.expenseApp.ui.viewmodels.ConfigurationViewModel
import com.pbs.expenseApp.ui.viewmodels.UserViewModel
import com.pbs.expenseApp.utils.AppUtils
import kotlinx.coroutines.async

@Preview(showBackground = true)
@Composable
fun Configuration() {
    val context = LocalContext.current

    val userVM: UserViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val configurationMV: ConfigurationViewModel = viewModel(factory = AppViewModelProvider.Factory)

    val monthlySavings = userVM.monthlySavings.observeAsState()
    val editMonthlySavings = configurationMV.editMonthlySavings.observeAsState()

    LaunchedEffect(key1 = true) {
        async { userVM.getMonthlySavings(AppUtils.appId(context)) }.await()
    }

    AppColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_sm))
    ) {
        AppCard {
             AppRow(modifier = Modifier
                 .fillMaxSize()
                 .padding(
                     start = dimensionResource(id = R.dimen.padding_sm),
                     end = dimensionResource(id = R.dimen.padding_sm)
                 )
             ) {
                MyMonthlySavingText(monthlySavings = monthlySavings.value ?: 0)
                Spacer(modifier = Modifier.weight(1f))
                if (!editMonthlySavings.value!!) {
                    AppIcon(
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = "Edit icon",
                        modifier = Modifier.clickable {
                            configurationMV.canEditMonthlySavings()
                        }
                    )
                }
            }
        }
        if (editMonthlySavings.value!!) {
            MyMonthlySavingModalBottomSheet(userVM = userVM, configurationMV = configurationMV)
        }
    }
}
