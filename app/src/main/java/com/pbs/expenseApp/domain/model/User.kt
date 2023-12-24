package com.pbs.expenseApp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey()
    val uuid: String,
    val monthlySavings: Int
)
