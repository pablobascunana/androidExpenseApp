package com.pbs.expenseApp.ui.screens.user

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.pbs.expenseApp.ui.database.entities.User
import com.pbs.expenseApp.ui.database.repositories.UserRepository

class UserEntryViewModel(private val userRepository: UserRepository): ViewModel() {
    var userUiState by mutableStateOf(UserUiState())
        private set

    fun updateUiState(userDetails: UserDetails) {
        userUiState = UserUiState(
            userDetails = userDetails,
        )
    }

    suspend fun saveUser(uuid: String) {
        userRepository.insertUser(userUiState.userDetails.toUser(uuid))
    }
}

data class UserUiState(
    val userDetails: UserDetails = UserDetails(),
)

data class UserDetails(
    val uuid: String = "",
    val monthlySavings: String = "0",
)

fun UserDetails.toUser(uuid: String): User = User(
    uuid = uuid,
    monthlySavings = monthlySavings.toDoubleOrNull() ?: 0.0,
)

fun User.toUserUiState(isEntryValid: Boolean = false): UserUiState = UserUiState(
    userDetails = this.toUserDetails()
)

fun User.toUserDetails(): UserDetails = UserDetails(
    uuid = uuid,
    monthlySavings = monthlySavings.toString()
)
