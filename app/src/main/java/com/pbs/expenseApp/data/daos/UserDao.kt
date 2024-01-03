package com.pbs.expenseApp.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.pbs.expenseApp.domain.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)
    @Update
    suspend fun update(user: User)
    @Query("SELECT EXISTS(SELECT * FROM users WHERE uuid = :uuid)")
    suspend fun userExists(uuid : String): Boolean
    @Query("SELECT monthlySavings FROM users WHERE uuid = :uuid")
    suspend fun getMonthlySavings(uuid: String): Int
}
