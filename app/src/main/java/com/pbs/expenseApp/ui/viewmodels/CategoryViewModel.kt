package com.pbs.expenseApp.ui.viewmodels

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pbs.expenseApp.R
import com.pbs.expenseApp.database.entities.Category
import com.pbs.expenseApp.database.entities.CategoryType
import com.pbs.expenseApp.database.repositories.CategoryRepository
import kotlinx.coroutines.launch
import java.util.UUID

class CategoryViewModel(
    private val categoryRepository: CategoryRepository,
    context: Context
): ViewModel() {
    private val incomeType: String = "INCOME"
    private val expenseType: String = "EXPENSE"

    val defaultCategories: List<DefaultCategory> = listOf(
        DefaultCategory(uuid = getUuid(), type = incomeType, name = getString(context = context, id = R.string.income_debts)),
        DefaultCategory(uuid = getUuid(), type = incomeType, name = getString(context = context, id = R.string.income_deposit)),
        DefaultCategory(uuid = getUuid(), type = incomeType, name = getString(context = context, id = R.string.income_salary)),
        DefaultCategory(uuid = getUuid(), type = incomeType, name = getString(context = context, id = R.string.income_savings)),
        DefaultCategory(uuid = getUuid(), type = expenseType, name = getString(context = context, id = R.string.expense_bills)),
        DefaultCategory(uuid = getUuid(), type = expenseType, name = getString(context = context, id = R.string.expense_car)),
        DefaultCategory(uuid = getUuid(), type = expenseType, name = getString(context = context, id = R.string.expense_clothes)),
        DefaultCategory(uuid = getUuid(), type = expenseType, name = getString(context = context, id = R.string.expense_entertainment)),
        DefaultCategory(uuid = getUuid(), type = expenseType, name = getString(context = context, id = R.string.expense_food)),
        DefaultCategory(uuid = getUuid(), type = expenseType, name = getString(context = context, id = R.string.expense_health)),
        DefaultCategory(uuid = getUuid(), type = expenseType, name = getString(context = context, id = R.string.expense_house)),
        DefaultCategory(uuid = getUuid(), type = expenseType, name = getString(context = context, id = R.string.expense_pets)),
        DefaultCategory(uuid = getUuid(), type = expenseType, name = getString(context = context, id = R.string.expense_presents)),
        DefaultCategory(uuid = getUuid(), type = expenseType, name = getString(context = context, id = R.string.expense_restaurants)),
        DefaultCategory(uuid = getUuid(), type = expenseType, name = getString(context = context, id = R.string.expense_sports)),
        DefaultCategory(uuid = getUuid(), type = expenseType, name = getString(context = context, id = R.string.expense_transports))
    )
    var categoryUiState by mutableStateOf(CategoryUiState())
        private set

    fun updateUiState(categoryDetails: CategoryDetails) {
        categoryUiState = CategoryUiState(
            categoryDetails = categoryDetails,
        )
    }

    suspend fun saveCategories(categories: List<DefaultCategory>, id: String) {
        viewModelScope.launch {
            categoryRepository.insertAllCategories(
                categoryUiState.categoryDetails.toCategory(categories = categories, id = id)
            )
        }
    }
}

data class CategoryUiState(
    val categoryDetails: CategoryDetails = CategoryDetails(),
)

data class CategoryDetails(
    val uuid: String = "",
    val name: String = "",
    val type: String = ""
)

fun CategoryDetails.toCategory(categories: List<DefaultCategory>, id: String): List<Category> {
    var toCategories = mutableListOf<Category>()
    categories.forEach { category ->
        toCategories.add(
            Category(
                uuid = category.uuid,
                userUuid = id,
                name = category.name,
                type = toCategoryType(category.type)
            )
        )
    }
    return toCategories
}

fun Category.toCategoryUiState(): CategoryUiState = CategoryUiState(
    categoryDetails = this.toCategoryDetails()
)

fun Category.toCategoryDetails(): CategoryDetails = CategoryDetails(
    uuid = uuid,
    name = name,
    type = type.toString()
)

data class DefaultCategory(
    val uuid: String,
    val type: String,
    val name: String
)

private fun getUuid(): String {
    return UUID.randomUUID().toString()
}

private fun getString(context: Context, id: Int): String {
    return context.getString(id)
}

private fun toCategoryType(type: String): CategoryType {
    return CategoryType.valueOf(type)
}