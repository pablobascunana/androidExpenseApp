package com.pbs.expenseApp.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.pbs.expenseApp.data.converters.DateConverters
import java.time.LocalDate

enum class MethodType(var value: String) {
    CASH(value = "cash"),
    CREDIT(value = "credit"),
    DEBIT(value = "debit"),
    TRANSFER(value = "transfer"),
    BIZUM(value = "bizum")
}

@Entity(
    tableName = "expenses",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["uuid"],
            childColumns = ["userUuid"],
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = Category::class,
            parentColumns = ["uuid"],
            childColumns = ["categoryUuid"],
            onDelete = CASCADE
        ),
    ],
    indices = [
        Index(value = ["userUuid"]),
        Index(value = ["categoryUuid"])
    ]
)
@TypeConverters(DateConverters::class)
data class Expense(
    @PrimaryKey()
    val uuid: String = "",
    val userUuid: String = "",
    val categoryUuid: String = "",
    val amount: Float = 0f,
    val creationDate: LocalDate? = null,
    val payMethod: MethodType = MethodType.CREDIT,
    val description: String = ""
)
