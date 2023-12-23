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

    val categoryTypes = enumValues<CategoryType>()

    var categories by mutableStateOf(emptyList<Category>())
    var expandedCategoryTypeDropDown by mutableStateOf(false)
    var categoryName by mutableStateOf("")
    var categoryType by mutableStateOf("")
    var canSaveCategory by mutableStateOf(false)

    suspend fun saveCategories(categories: List<Category> = defaultCategories) {
        viewModelScope.launch {
            categoryRepository.insertAllCategories(categories = categories)
        }
    }
    suspend fun saveCategory(name: String, type: CategoryType) {
        val uuid = AppUtils.getUuid()
        val category = Category(
            uuid = uuid,
            userUuid = AppUtils.getAppId(appContext),
            name = name,
            type = type,
            isDefault = false,
        )
        viewModelScope.launch {
            viewModelScope.async {
                categoryRepository.insertCategory(category = category)
            }.await()
        }
    }

    init {
        viewModelScope.launch {
            getCategories()
        }
    }
    private fun getCategories() {
        viewModelScope.launch {
            categoryRepository.getAllCategoriesOrderByType().collect { response: List<Category> ->
                categories = response
            }
        }
    }

    fun isExpandedCategoryTypeDropdown() {
        expandedCategoryTypeDropDown = !expandedCategoryTypeDropDown
    }

    fun canSaveCategory() {
        canSaveCategory = !canSaveCategory
    }
}
