package com.pbs.expenseApp.data.repository

import com.pbs.expenseApp.data.daos.UserDao
import com.pbs.expenseApp.domain.model.User
import com.pbs.expenseApp.domain.repository.UserRepository

class UserRepositoryImpl(private val userDao: UserDao): UserRepository {
    override suspend fun insertUser(user: User) = userDao.insert(user)
    override suspend fun updateUser(user: User) = userDao.update(user)
    override suspend fun userExists(uuid: String): Boolean = userDao.userExists(uuid = uuid)
    override suspend fun getMonthlySavings(uuid: String) = userDao.getMonthlySavings(uuid = uuid)
}
