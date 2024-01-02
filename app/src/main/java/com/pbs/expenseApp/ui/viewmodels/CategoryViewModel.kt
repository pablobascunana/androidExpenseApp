package com.pbs.expenseApp.ui.viewmodels

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pbs.expenseApp.R
import com.pbs.expenseApp.domain.model.Category
import com.pbs.expenseApp.domain.model.CategoryType
import com.pbs.expenseApp.domain.repository.CategoryRepository
import com.pbs.expenseApp.utils.AppUtils
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val categoryRepository: CategoryRepository,
    context: Context
): ViewModel() {

    private val appContext = context

    private val defaultCategories: List<Category> = listOf(
        Category(uuid = AppUtils.getUuid(), userUuid = AppUtils.getAppId(context), type = CategoryType.INCOME, name = AppUtils.getString(context = context, id = R.string.income_salary)),
        Category(uuid = AppUtils.getUuid(), userUuid = AppUtils.getAppId(context), type = CategoryType.INCOME, name = AppUtils.getString(context = context, id = R.string.income_deposit)),
        Category(uuid = AppUtils.getUuid(), userUuid = AppUtils.getAppId(context), type = CategoryType.INCOME, name = AppUtils.getString(context = context, id = R.string.income_savings)),
        Category(uuid = AppUtils.getUuid(), userUuid = AppUtils.getAppId(context), type = CategoryType.INCOME, name = AppUtils.getString(context = context, id = R.string.income_debts)),
        Category(uuid = AppUtils.getUuid(), userUuid = AppUtils.getAppId(context), type = CategoryType.EXPENSE, name = AppUtils.getString(context = context, id = R.string.expense_bills)),
        Category(uuid = AppUtils.getUuid(), userUuid = AppUtils.getAppId(context), type = CategoryType.EXPENSE, name = AppUtils.getString(context = context, id = R.string.expense_car)),
        Category(uuid = AppUtils.getUuid(), userUuid = AppUtils.getAppId(context), type = CategoryType.EXPENSE, name = AppUtils.getString(context = context, id = R.string.expense_clothes)),
        Category(uuid = AppUtils.getUuid(), userUuid = AppUtils.getAppId(context), type = CategoryType.EXPENSE, name = AppUtils.getString(context = context, id = R.string.expense_entertainment)),
        Category(uuid = AppUtils.getUuid(), userUuid = AppUtils.getAppId(context), type = CategoryType.EXPENSE, name = AppUtils.getString(context = context, id = R.string.expense_food)),
        Category(uuid = AppUtils.getUuid(), userUuid = AppUtils.getAppId(context), type = CategoryType.EXPENSE, name = AppUtils.getString(context = context, id = R.string.expense_health)),
        Category(uuid = AppUtils.getUuid(), userUuid = AppUtils.getAppId(context), type = CategoryType.EXPENSE, name = AppUtils.getString(context = context, id = R.string.expense_house)),
        Category(uuid = AppUtils.getUuid(), userUuid = AppUtils.getAppId(context), type = CategoryType.EXPENSE, name = AppUtils.getString(context = context, id = R.string.expense_pets)),
        Category(uuid = AppUtils.getUuid(), userUuid = AppUtils.getAppId(context), type = CategoryType.EXPENSE, name = AppUtils.getString(context = context, id = R.string.expense_presents)),
        Category(uuid = AppUtils.getUuid(), userUuid = AppUtils.getAppId(context), type = CategoryType.EXPENSE, name = AppUtils.getString(context = context, id = R.string.expense_restaurants)),
        Category(uuid = AppUtils.getUuid(), userUuid = AppUtils.getAppId(context), type = CategoryType.EXPENSE, name = AppUtils.getString(context = context, id = R.string.expense_sports)),
        Category(uuid = AppUtils.getUuid(), userUuid = AppUtils.getAppId(context), type = CategoryType.EXPENSE, name = AppUtils.getString(context = context, id = R.string.expense_transports))
    )

    private val categoryTypes = enumValues<CategoryType>()

    var categories by mutableStateOf(emptyList<Category>())
    var categoryName by mutableStateOf("")
    var canInsertCategory by mutableStateOf(false)
    var categorySelected by mutableStateOf(
        Category(uuid = "", userUuid = "", type = CategoryType.EXPENSE, name = "" )
    )
    var canEditCategory by mutableStateOf(false)
    var canDeleteCategory by mutableStateOf(false)
    var confirmDelete by mutableStateOf(false)

    suspend fun insertAll(categories: List<Category> = defaultCategories) {
        viewModelScope.launch {
            categoryRepository.insertAll(categories = categories)
        }
    }
    suspend fun insert(name: String, type: CategoryType) {
        val category = toCategory(name = name, type = type)
        viewModelScope.launch {
            viewModelScope.async {
                categoryRepository.insert(category = category)
            }.await()
        }
    }
    suspend fun update(category: Category) {
        viewModelScope.launch {
            async { categoryRepository.update(category = category) }.await()
        }
    }
    suspend fun delete(category: Category) {
        viewModelScope.launch {
            async { categoryRepository.delete(category = category) }.await()
        }
    }
    private fun getCategories() {
        viewModelScope.launch {
            categoryRepository.getAllOrderByType().collect { response: List<Category> ->
                categories = response
            }
        }
    }
    init {
        viewModelScope.launch {
            getCategories()
        }
    }
    private fun toCategory(
        uuid: String = AppUtils.getUuid(), name: String, type: CategoryType
    ): Category {
        return Category(
            uuid = uuid,
            userUuid = AppUtils.getAppId(appContext),
            name = name,
            type = type,
            isDefault = false,
        )
    }

    fun getCategoryType(category: String): CategoryType {
        return categoryTypeToEnum(category = category)
    }

    fun categoryTypeToString(context: Context, type: String): String {
        return when (type) {
            CategoryType.INCOME.value -> AppUtils.getString(
                context = context, id = R.string.category_type_income
            )
            else -> AppUtils.getString(context = context, id = R.string.category_type_expense)
        }
    }

    fun getCategoryTypes(): Array<CategoryType> {
        categoryTypes.forEach { category ->
            category.value = categoryTypeToString(context = appContext, type = category.value)
        }
        return categoryTypes
    }

    fun categoryTypeToEnum(category: String): CategoryType {
        return when (category) {
            AppUtils.getString(context = appContext, id = R.string.category_type_income) -> CategoryType.INCOME
            else -> CategoryType.EXPENSE
        }
    }
}
