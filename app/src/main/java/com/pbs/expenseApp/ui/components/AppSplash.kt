package com.pbs.expenseApp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Savings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.pbs.expenseApp.R

@Composable
fun AppSplash() {
    AppColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AppIcon(
            imageVector = Icons.Default.Savings,
            contentDescription = stringResource(R.string.splash_image),
            modifier = Modifier.size(
                dimensionResource(R.dimen.splash_width_height),
                dimensionResource(R.dimen.splash_width_height)
            )
        )
    }
}
