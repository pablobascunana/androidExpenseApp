package com.pbs.expenseApp.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.pbs.expenseApp.database.entities.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)
    @Update
    suspend fun update(user: User)
    @Delete
    suspend fun delete(user: User)
    @Query("SELECT EXISTS(SELECT * FROM users WHERE uuid = :uuid)")
    suspend fun isUserExist(uuid : String): Boolean
    @Query("SELECT * from users WHERE uuid = :uuid")
    fun getUser(uuid: String): Flow<User>
    @Query("SELECT * from users")
    fun getAllUsers(): Flow<List<User>>
    @Query("SELECT monthlySavings from users where uuid = :uuid")
    suspend fun getMonthlySavings(uuid: String): Int
}
