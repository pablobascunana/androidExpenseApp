package com.pbs.expenseApp.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pbs.expenseApp.R
import com.pbs.expenseApp.database.entities.Category
import com.pbs.expenseApp.database.entities.CategoryType
import com.pbs.expenseApp.ui.AppViewModelProvider
import com.pbs.expenseApp.ui.components.AppCard
import com.pbs.expenseApp.ui.components.AppColumn
import com.pbs.expenseApp.ui.components.AppIcon
import com.pbs.expenseApp.ui.components.AppLazyVerticalGrid
import com.pbs.expenseApp.ui.components.AppRow
import com.pbs.expenseApp.ui.components.AppText
import com.pbs.expenseApp.ui.composables.MyMonthlySavingModalBottomSheet
import com.pbs.expenseApp.ui.composables.MyMonthlySavingText
import com.pbs.expenseApp.ui.composables.fontDimensionResource
import com.pbs.expenseApp.ui.viewmodels.CardItem
import com.pbs.expenseApp.ui.viewmodels.CategoryViewModel
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
    val categoriesMV: CategoryViewModel = viewModel(factory = AppViewModelProvider.Factory)

    val monthlySavings = userVM.monthlySavings.observeAsState()
    val editMonthlySavings = configurationMV.editMonthlySavings.observeAsState()
    val cardItems = configurationMV.cardItems.observeAsState()
    val categories = categoriesMV.categories.observeAsState()

    LaunchedEffect(key1 = true) {
        async { userVM.getMonthlySavings(AppUtils.appId(context)) }.await()
    }

    cardItems.value!!.forEach { item ->
        GetCardColors(item)
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
                     end = dimensionResource(id = R.dimen.padding_xs)
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
        AppLazyVerticalGrid {
            items(cardItems.value!!) { item ->
                AppCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dimensionResource(R.dimen.card_height_lg))
                        .padding(
                            bottom = dimensionResource(R.dimen.padding_xs)
                        ),
                    containerColor = item.containerColor!!
                ) {
                    AppRow(modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            start = dimensionResource(id = R.dimen.padding_sm),
                            end = dimensionResource(id = R.dimen.padding_sm)
                        )
                    ) {
                        AppText(text = stringResource(id = item.title))
                    }
                }
            }
        }
        Text(text = stringResource(id = R.string.configuration_category_title),
            fontSize = fontDimensionResource(id = R.dimen.font_md),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_sm))
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(categories.value!!) { category ->
                AppCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = dimensionResource(id = R.dimen.padding_xs))
                        .height(dimensionResource(R.dimen.card_height)),
                    containerColor = getCategoryCardColor(category)
                ) {
                    AppRow(modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            start = dimensionResource(id = R.dimen.padding_sm),
                            end = dimensionResource(id = R.dimen.padding_sm)
                        )
                    ) {
                        Text(text = category.name)
                    }
                }
            }
        }
        if (editMonthlySavings.value!!) {
            MyMonthlySavingModalBottomSheet(userVM = userVM, configurationMV = configurationMV)
        }
    }
}


@Composable
fun GetCardColors(item: CardItem) {
    when (item.title) {
        R.string.configuration_card_savings ->
            item.containerColor = MaterialTheme.colorScheme.tertiaryContainer
        R.string.configuration_card_expenses ->
            item.containerColor = MaterialTheme.colorScheme.errorContainer
        else -> item.containerColor = MaterialTheme.colorScheme.secondaryContainer
    }
}

@Composable
fun getCategoryCardColor(item: Category): Color {
    if (item.type == CategoryType.INCOME) {
        return MaterialTheme.colorScheme.tertiaryContainer
    }
    return MaterialTheme.colorScheme.errorContainer
}
