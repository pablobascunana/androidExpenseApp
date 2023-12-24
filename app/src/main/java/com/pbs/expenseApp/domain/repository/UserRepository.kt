package com.pbs.expenseApp.domain.repository

import com.pbs.expenseApp.domain.model.User

interface UserRepository {
    suspend fun insertUser(user: User)
    suspend fun updateUser(user: User)
    suspend fun userExists(uuid: String): Boolean
    suspend fun getMonthlySavings(uuid: String): Int
}
