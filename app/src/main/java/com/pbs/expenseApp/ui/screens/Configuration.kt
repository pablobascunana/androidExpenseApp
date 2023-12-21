package com.pbs.expenseApp.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pbs.expenseApp.R
import com.pbs.expenseApp.ui.AppViewModelProvider
import com.pbs.expenseApp.ui.components.AppCard
import com.pbs.expenseApp.ui.components.AppColumn
import com.pbs.expenseApp.ui.components.AppIcon
import com.pbs.expenseApp.ui.components.AppRow
import com.pbs.expenseApp.ui.components.AppText
import com.pbs.expenseApp.ui.composables.MyMonthlySavingModalBottomSheet
import com.pbs.expenseApp.ui.composables.MyMonthlySavingText
import com.pbs.expenseApp.ui.viewmodels.CardItem
import com.pbs.expenseApp.ui.viewmodels.ConfigurationViewModel
import com.pbs.expenseApp.ui.viewmodels.UserViewModel
import com.pbs.expenseApp.utils.AppUtils
import kotlinx.coroutines.async

@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun Configuration() {
    val context = LocalContext.current

    val userVM: UserViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val configurationMV: ConfigurationViewModel = viewModel(factory = AppViewModelProvider.Factory)

    val monthlySavings = userVM.monthlySavings.observeAsState()
    val editMonthlySavings = configurationMV.editMonthlySavings.observeAsState()
    val cardItems = configurationMV.cardItems.observeAsState()
    cardItems.value!!.forEach { item ->
        FillCardItems(item)
    }

    LaunchedEffect(key1 = true) {
        async { userVM.getMonthlySavings(AppUtils.appId(context)) }.await()
    }

    AppColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_sm))
    ) {
        AppCard(modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(R.dimen.card_height))
        ) {
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

        LazyVerticalGrid(
            columns = GridCells.Fixed(count = 2),
            modifier = Modifier.padding(
                top = dimensionResource(id = R.dimen.padding_sm),
                bottom = dimensionResource(id = R.dimen.padding_sm),
            ),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_sm)),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_sm))
        ) {
            items(cardItems.value!!) { item ->
                AppCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dimensionResource(R.dimen.card_height_lg)),
                    containerColor = item.containerColor!!,
                    borderColor = item.borderColor!!
                ) {
                    AppRow(modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            start = dimensionResource(id = R.dimen.padding_sm),
                            end = dimensionResource(id = R.dimen.padding_sm)
                        )
                    ) {
                        AppText(id = item.title)
                    }
                }
            }
        }
    }
}

@Composable
fun FillCardItems(item: CardItem) {
    if (item.title == R.string.configuration_card_savings) {
        item.containerColor = MaterialTheme.colorScheme.tertiaryContainer
        item.borderColor = MaterialTheme.colorScheme.onTertiaryContainer
    } else if (item.title == R.string.configuration_card_expenses) {
        item.containerColor = MaterialTheme.colorScheme.errorContainer
        item.borderColor = MaterialTheme.colorScheme.onErrorContainer
    } else {
        item.containerColor = MaterialTheme.colorScheme.primaryContainer
        item.borderColor = MaterialTheme.colorScheme.inversePrimary
    }
}
