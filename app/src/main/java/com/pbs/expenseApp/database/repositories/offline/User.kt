package com.pbs.expenseApp.database.repositories.offline

import com.pbs.expenseApp.database.daos.UserDao
import com.pbs.expenseApp.database.entities.User
import com.pbs.expenseApp.database.repositories.UserRepository
import kotlinx.coroutines.flow.Flow

class OfflineUserRepository(private val userDao: UserDao): UserRepository {
    override suspend fun insertUser(user: User) = userDao.insert(user)
    override suspend fun updateUser(user: User) = userDao.update(user)
    override suspend fun deleteUser(user: User) = userDao.delete(user)
    override suspend fun isUserExists(uuid: String): Boolean = userDao.isUserExist(uuid = uuid)
    override fun getUserStream(uuid: String): Flow<User?> = userDao.getUser(uuid = uuid)
    override fun getAllUsersStream(): Flow<List<User>> = userDao.getAllUsers()
    override suspend fun getMonthlySavings(uuid: String): Int = userDao.getMonthlySavings(uuid = uuid)
}
