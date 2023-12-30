package com.pbs.expenseApp.ui.composables.addmonthlymovements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.pbs.expenseApp.R
import com.pbs.expenseApp.navigation.AppRoutes
import com.pbs.expenseApp.ui.components.AppCard
import com.pbs.expenseApp.ui.components.AppLazyVerticalGrid
import com.pbs.expenseApp.ui.components.AppRow
import com.pbs.expenseApp.ui.components.AppText
import com.pbs.expenseApp.ui.viewmodels.CardItem
import com.pbs.expenseApp.utils.AppUtils

@Composable
fun MyAddIncomesAndExpensesGrid(
    navHostController: NavHostController,
    cardItems: List<CardItem>
) {
    val context = LocalContext.current
    AppLazyVerticalGrid {
        items(cardItems) { item ->
            AppCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(R.dimen.card_height_md))
                    .padding(
                        bottom = dimensionResource(R.dimen.padding_xs)
                    )
                    .clickable {
                        navHostController.navigate(
                            route = AppRoutes.AddMovement.route + "/${
                                AppUtils.getString(context = context, id = item.type)
                            }"
                        )
                    },
                containerColor = item.containerColor!!
            ) {
                AppRow(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(id = R.dimen.padding_md))
                ) {
                    AppText(text = stringResource(id = item.title))
                }
            }
        }
    }
}
