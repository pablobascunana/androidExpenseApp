package com.pbs.expenseApp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pbs.expenseApp.database.entities.User
import com.pbs.expenseApp.database.repositories.UserRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository): ViewModel() {

    init {
        val users = userRepository.getAllUsersStream()
    }

    suspend fun userExists(uuid: String): Boolean {
        var userExists = false
        viewModelScope.async() {
            userExists = userRepository.isUserExists(uuid)
        }.await()
        return userExists
    }

    fun insertUser(uuid: String) {
        val user = createUser(uuid = uuid)
        viewModelScope.launch {
            async {
                userRepository.insertUser(user)
            }.await()
        }
    }

    suspend fun getMonthlySavings(uuid: String): Int {
        var monthlySavings = 0
        viewModelScope.async {
            monthlySavings = userRepository.getMonthlySavings(uuid)
        }.await()
        return monthlySavings
    }
}

// TODO: probably rename function similar to userUiState to User
fun createUser(uuid: String, monthlySavings: Int = 0): User {
    return User(uuid = uuid, monthlySavings = monthlySavings)
}
