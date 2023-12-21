package com.pbs.expenseApp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.pbs.expenseApp.R

@Composable
fun AppLazyVerticalGrid(
    verticalGridContent: LazyGridScope.() -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(count = 2),
        modifier = Modifier
            .padding(
                top = dimensionResource(id = R.dimen.padding_sm),
                bottom = dimensionResource(id = R.dimen.padding_xs)
            ),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_sm)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_sm))
    ) {
        verticalGridContent()
    }
}
