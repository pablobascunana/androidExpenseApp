package com.pbs.expenseApp.ui.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.pbs.expenseApp.R
import com.pbs.expenseApp.ui.components.AppCard
import com.pbs.expenseApp.ui.components.AppLazyVerticalGrid
import com.pbs.expenseApp.ui.components.AppRow
import com.pbs.expenseApp.ui.components.AppText
import com.pbs.expenseApp.ui.viewmodels.CardItem

@Composable
fun MyAddIncomesAndExpensesGrid(cardItems: List<CardItem>) {
    AppLazyVerticalGrid {
        items(cardItems) { item ->
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
}
