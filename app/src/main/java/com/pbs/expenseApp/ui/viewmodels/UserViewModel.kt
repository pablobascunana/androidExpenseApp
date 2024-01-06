package com.pbs.expenseApp.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.pbs.expenseApp.domain.model.User
import com.pbs.expenseApp.domain.repository.UserRepository

class UserViewModel(private val userRepository: UserRepository): ViewModel() {

    var userExists by mutableStateOf(false)
    var user by mutableStateOf(User())

    suspend fun getUser(uuid: String) {
        user = userRepository.getUser(uuid)
    }
    suspend fun userExists(uuid: String) {
        userExists = userRepository.userExists(uuid)
    }
    suspend fun insertUser(user: User) {
        userRepository.insertUser(user)
    }
    suspend fun updateUser() {
        userRepository.updateUser(user)
    }
}
