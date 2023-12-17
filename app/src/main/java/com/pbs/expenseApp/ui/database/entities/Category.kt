package com.pbs.expenseApp.ui.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class CategoryType(val value: String) {
    INCOME(value = "income"),
    EXPENSE(value = "expense")
}

@Entity(
    tableName = "categories",
    foreignKeys = [
        androidx.room.ForeignKey(
            entity = User::class,
            parentColumns = ["uuid"],
            childColumns = ["userUuid"],
            onDelete = androidx.room.ForeignKey.Companion.CASCADE
        )
    ])
data class Category(
    @PrimaryKey()
    val uuid: String,
    val userUuid: String,
    val name: String,
    val type: CategoryType
)
