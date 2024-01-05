package com.pbs.expenseApp.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.pbs.expenseApp.domain.model.User
import com.pbs.expenseApp.domain.repository.UserRepository

class UserViewModel(private val userRepository: UserRepository): ViewModel() {

    var userExists by mutableStateOf(false)
    var monthlySavings by mutableIntStateOf(0)

    suspend fun userExists(uuid: String) {
        userExists = userRepository.userExists(uuid)
    }
    suspend fun insertUser(uuid: String) {
        userRepository.insertUser(toUser(uuid = uuid))
    }
    suspend fun updateUser(uuid: String, savings: Int) {
        userRepository.updateUser(toUser(uuid = uuid, monthlySavings = savings))
        monthlySavings = savings
    }
    suspend fun getMonthlySavings(uuid: String) {
        monthlySavings = userRepository.getMonthlySavings(uuid)
    }
    private fun toUser(uuid: String, monthlySavings: Int = 0): User {
        return User(uuid = uuid, monthlySavings = monthlySavings)
    }
}
