package com.pbs.expenseApp.ui.screens.user

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class UserEditViewModel(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    var userUiState by mutableStateOf(UserUiState())
        private set

    // private val itemId: Int = checkNotNull(savedStateHandle[UserEditDestination.itemIdArg])
}