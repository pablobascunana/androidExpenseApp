package com.pbs.expenseApp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pbs.expenseApp.data.daos.UserDao
import com.pbs.expenseApp.database.converters.DateConverters
import com.pbs.expenseApp.database.daos.CategoryDao
import com.pbs.expenseApp.database.daos.ExpenseDao
import com.pbs.expenseApp.database.entities.Category
import com.pbs.expenseApp.database.entities.Expense
import com.pbs.expenseApp.domain.model.User

@Database(
    entities = [
        User::class,
        Category::class,
        Expense::class
    ],
    version = 1,
    exportSchema = false
)
abstract class ExpenseDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun categoryDao(): CategoryDao
    abstract fun expenseDao(): ExpenseDao

    companion object {
        private const val DATABASE_NAME = "expense_database"
        private val dateConverter = DateConverters()
        @Volatile
        private var Instance: ExpenseDatabase? = null
        fun getDatabase(context: Context): ExpenseDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, ExpenseDatabase::class.java, name = DATABASE_NAME)
                    .addTypeConverter(dateConverter)
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
