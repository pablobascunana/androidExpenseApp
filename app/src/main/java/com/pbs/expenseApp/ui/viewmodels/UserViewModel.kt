package com.pbs.expenseApp.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pbs.expenseApp.database.entities.User
import com.pbs.expenseApp.database.repositories.UserRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository): ViewModel() {

    var userExists = MutableLiveData(false)
    var monthlySavings = MutableLiveData(0)

    suspend fun userExists(uuid: String) {
        viewModelScope.async() {
            userExists.value = userRepository.isUserExists(uuid)
        }.await()
    }

    fun insertUser(uuid: String) {
        val user = createUser(uuid = uuid)
        viewModelScope.launch {
            async {
                userRepository.insertUser(user)
            }.await()
        }
    }

    suspend fun getMonthlySavings(uuid: String) {
        viewModelScope.async {
            monthlySavings.value = userRepository.getMonthlySavings(uuid)
        }.await()
    }
}

// TODO: probably rename function similar to userUiState to User
fun createUser(uuid: String, monthlySavings: Int = 0): User {
    return User(uuid = uuid, monthlySavings = monthlySavings)
}
