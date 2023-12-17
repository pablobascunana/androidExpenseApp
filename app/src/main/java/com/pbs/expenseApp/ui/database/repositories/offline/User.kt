package com.pbs.expenseApp.ui.database.repositories.offline

import com.pbs.expenseApp.ui.database.daos.UserDao
import com.pbs.expenseApp.ui.database.entities.User
import com.pbs.expenseApp.ui.database.repositories.UserRepository
import kotlinx.coroutines.flow.Flow

class OfflineUserRepository(private val userDao: UserDao): UserRepository {
    override suspend fun insertUser(user: User) = userDao.insert(user)
    override suspend fun updateUser(user: User) = userDao.update(user)
    override suspend fun deleteUser(user: User) = userDao.delete(user)
    override fun getUserStream(uuid: String): Flow<User?> = userDao.getUser(uuid = "uuid")
    override fun getAllUsersStream(): Flow<List<User>> = userDao.getAllUsers()
}
