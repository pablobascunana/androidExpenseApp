package com.pbs.expenseApp.database.repositories

import com.pbs.expenseApp.database.entities.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun insertUser(user: User)
    suspend fun updateUser(user: User)
    suspend fun deleteUser(user: User)
    suspend fun isUserExists(uuid: String): Boolean
    fun getUserStream(uuid: String): Flow<User?>
    fun getAllUsersStream(): Flow<List<User>>
}
