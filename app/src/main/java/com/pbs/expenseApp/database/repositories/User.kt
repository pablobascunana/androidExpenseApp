package com.pbs.expenseApp.database.repositories

import androidx.lifecycle.LiveData
import com.pbs.expenseApp.database.entities.User

interface UserRepository {
    suspend fun insertUser(user: User)
    suspend fun updateUser(user: User)
    suspend fun deleteUser(user: User)
    suspend fun isUserExists(uuid: String): Boolean
    fun getUserStream(uuid: String): LiveData<User?>
    fun getAllUsersStream(): LiveData<List<User>>
    suspend fun getMonthlySavings(uuid: String): Int
}
