package com.pbs.expenseApp.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.pbs.expenseApp.R

@Composable
fun AppCard(cardContent: @Composable() () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = dimensionResource(id = R.dimen.card_elevation)
        ),
        border = BorderStroke(
            dimensionResource(id = R.dimen.card_elevation),
            color = MaterialTheme.colorScheme.inversePrimary
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(R.dimen.card_height))
    ) {
        cardContent()
    }
}
