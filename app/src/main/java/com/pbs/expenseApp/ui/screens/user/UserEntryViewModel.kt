package com.pbs.expenseApp.ui.screens.user

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pbs.expenseApp.database.entities.User
import com.pbs.expenseApp.database.repositories.UserRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class UserEntryViewModel(private val userRepository: UserRepository): ViewModel() {
    var userUiState by mutableStateOf(UserUiState())
        private set

    fun updateUiState(userDetails: UserDetails) {
        userUiState = UserUiState(
            userDetails = userDetails,
        )
    }

    suspend fun isUserExists(uuid: String): Boolean {
        var userExists = false
        viewModelScope.async {
            userExists = userRepository.isUserExists(uuid)
        }.await()
        return userExists
    }

    suspend fun saveUser(uuid: String) {
        viewModelScope.launch {
            userRepository.insertUser(userUiState.userDetails.toUser(uuid))
        }
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
    monthlySavings = monthlySavings.toIntOrNull() ?: 0,
)

fun User.toUserUiState(): UserUiState = UserUiState(
    userDetails = this.toUserDetails()
)

fun User.toUserDetails(): UserDetails = UserDetails(
    uuid = uuid,
    monthlySavings = monthlySavings.toString()
)
