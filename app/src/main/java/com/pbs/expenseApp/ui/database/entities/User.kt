package com.pbs.expenseApp.ui.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey()
    val uuid: String,
    val monthlySavings: Double
)