package com.pbs.expenseApp.domain.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

enum class CategoryType(var value: String) {
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
    ],
    indices = [
        Index(value = ["userUuid", "name"], unique = true)
    ]
)
data class Category(
    @PrimaryKey()
    val uuid: String = "",
    val userUuid: String = "",
    var name: String = "",
    var type: CategoryType = CategoryType.INCOME,
    val isDefault: Boolean = true
)
