package com.pbs.expenseApp.ui.database.repositories

import com.pbs.expenseApp.ui.database.entities.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun insertUser(user: User)
    suspend fun updateUser(user: User)
    suspend fun deleteUser(user: User)
    fun getUserStream(uuid: String): Flow<User?>
    fun getAllUsersStream(): Flow<List<User>>
}
