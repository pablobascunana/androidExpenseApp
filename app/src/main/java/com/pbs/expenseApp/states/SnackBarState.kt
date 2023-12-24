package com.pbs.expenseApp.states

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember

@Composable
fun rememberSnackBarState(
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() }
) = SnackBarState(snackBarHostState)


@Stable
class SnackBarState(
    private val snackBarHostState: SnackbarHostState
) {
    val state: SnackbarHostState
        get() = snackBarHostState
}