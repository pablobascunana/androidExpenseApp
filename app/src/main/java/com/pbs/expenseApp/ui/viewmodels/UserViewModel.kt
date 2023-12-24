package com.pbs.expenseApp.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pbs.expenseApp.domain.model.User
import com.pbs.expenseApp.domain.repository.UserRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository): ViewModel() {

    var userExists by mutableStateOf(false)
    var monthlySavings by mutableIntStateOf(0)

    suspend fun userExists(uuid: String) {
        viewModelScope.async {
            userExists = userRepository.userExists(uuid)
        }.await()
    }

    fun insertUser(uuid: String) {
        val user = toUser(uuid = uuid)
        viewModelScope.launch {
            async {
                userRepository.insertUser(user)
            }.await()
        }
    }

    suspend fun updateUser(uuid: String, savings: Int) {
        val user = toUser(uuid = uuid, monthlySavings = savings)
        viewModelScope.async {
            userRepository.updateUser(user)
            monthlySavings = savings
        }.await()
    }

    suspend fun getMonthlySavings(uuid: String) {
        viewModelScope.async {
            monthlySavings = userRepository.getMonthlySavings(uuid)
        }.await()
    }

    private fun toUser(uuid: String, monthlySavings: Int = 0): User {
        return User(uuid = uuid, monthlySavings = monthlySavings)
    }
}
