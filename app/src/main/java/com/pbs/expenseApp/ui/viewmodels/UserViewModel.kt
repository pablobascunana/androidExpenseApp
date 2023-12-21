package com.pbs.expenseApp.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pbs.expenseApp.database.entities.User
import com.pbs.expenseApp.database.repositories.UserRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository): ViewModel() {

    private val _userExists = MutableLiveData<Boolean>()
    val userExists: LiveData<Boolean>
        get() = _userExists

    private val _monthlySavings = MutableLiveData<Int>()
    val monthlySavings: LiveData<Int>
        get() = _monthlySavings

    suspend fun userExists(uuid: String) {
        viewModelScope.async() {
            _userExists.value = userRepository.isUserExists(uuid)
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

    suspend fun updateUser(uuid: String, savings: Int) {
        val user = createUser(uuid = uuid, monthlySavings = savings)
        viewModelScope.async {
            userRepository.updateUser(user)
            _monthlySavings.value = savings
        }.await()
    }

    suspend fun getMonthlySavings(uuid: String) {
        viewModelScope.async {
            val result = userRepository.getMonthlySavings(uuid)
            _monthlySavings.value = result
        }.await()
    }

    private fun createUser(uuid: String, monthlySavings: Int = 0): User {
        return User(uuid = uuid, monthlySavings = monthlySavings)
    }
}


