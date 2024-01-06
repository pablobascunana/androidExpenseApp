package com.pbs.expenseApp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.pbs.expenseApp.data.converters.DateConverters
import java.time.LocalDate

@Entity(tableName = "users")
@TypeConverters(DateConverters::class)
data class User (
    @PrimaryKey()
    val uuid: String = "",
    var monthlySavings: Int = 0,
    val creationDate: LocalDate = LocalDate.now()
)
