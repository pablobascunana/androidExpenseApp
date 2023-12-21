package com.pbs.expenseApp.ui.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pbs.expenseApp.R
import com.pbs.expenseApp.database.entities.Category
import com.pbs.expenseApp.database.entities.CategoryType
import com.pbs.expenseApp.database.repositories.CategoryRepository
import com.pbs.expenseApp.utils.AppUtils
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val categoryRepository: CategoryRepository,
    context: Context
): ViewModel() {

    private val defaultCategories: List<Category> = listOf(
        Category(uuid = AppUtils.getUuid(), userUuid = AppUtils.appId(context), type = CategoryType.INCOME, name = AppUtils.getString(context = context, id = R.string.income_salary)),
        Category(uuid = AppUtils.getUuid(), userUuid = AppUtils.appId(context), type = CategoryType.INCOME, name = AppUtils.getString(context = context, id = R.string.income_deposit)),
        Category(uuid = AppUtils.getUuid(), userUuid = AppUtils.appId(context), type = CategoryType.INCOME, name = AppUtils.getString(context = context, id = R.string.income_savings)),
        Category(uuid = AppUtils.getUuid(), userUuid = AppUtils.appId(context), type = CategoryType.INCOME, name = AppUtils.getString(context = context, id = R.string.income_debts)),
        Category(uuid = AppUtils.getUuid(), userUuid = AppUtils.appId(context), type = CategoryType.EXPENSE, name = AppUtils.getString(context = context, id = R.string.expense_bills)),
        Category(uuid = AppUtils.getUuid(), userUuid = AppUtils.appId(context), type = CategoryType.EXPENSE, name = AppUtils.getString(context = context, id = R.string.expense_car)),
        Category(uuid = AppUtils.getUuid(), userUuid = AppUtils.appId(context), type = CategoryType.EXPENSE, name = AppUtils.getString(context = context, id = R.string.expense_clothes)),
        Category(uuid = AppUtils.getUuid(), userUuid = AppUtils.appId(context), type = CategoryType.EXPENSE, name = AppUtils.getString(context = context, id = R.string.expense_entertainment)),
        Category(uuid = AppUtils.getUuid(), userUuid = AppUtils.appId(context), type = CategoryType.EXPENSE, name = AppUtils.getString(context = context, id = R.string.expense_food)),
        Category(uuid = AppUtils.getUuid(), userUuid = AppUtils.appId(context), type = CategoryType.EXPENSE, name = AppUtils.getString(context = context, id = R.string.expense_health)),
        Category(uuid = AppUtils.getUuid(), userUuid = AppUtils.appId(context), type = CategoryType.EXPENSE, name = AppUtils.getString(context = context, id = R.string.expense_house)),
        Category(uuid = AppUtils.getUuid(), userUuid = AppUtils.appId(context), type = CategoryType.EXPENSE, name = AppUtils.getString(context = context, id = R.string.expense_pets)),
        Category(uuid = AppUtils.getUuid(), userUuid = AppUtils.appId(context), type = CategoryType.EXPENSE, name = AppUtils.getString(context = context, id = R.string.expense_presents)),
        Category(uuid = AppUtils.getUuid(), userUuid = AppUtils.appId(context), type = CategoryType.EXPENSE, name = AppUtils.getString(context = context, id = R.string.expense_restaurants)),
        Category(uuid = AppUtils.getUuid(), userUuid = AppUtils.appId(context), type = CategoryType.EXPENSE, name = AppUtils.getString(context = context, id = R.string.expense_sports)),
        Category(uuid = AppUtils.getUuid(), userUuid = AppUtils.appId(context), type = CategoryType.EXPENSE, name = AppUtils.getString(context = context, id = R.string.expense_transports))
    )

    private val _categories = MutableLiveData(defaultCategories)

    val categories: LiveData<List<Category>>
        get() = _categories

    suspend fun saveCategories(categories: List<Category> = defaultCategories) {
        viewModelScope.launch {
            categoryRepository.insertAllCategories(categories = categories)
        }
    }

    init {
        viewModelScope.launch {
            getCategories()
        }
    }
    private suspend fun getCategories() {
        _categories.value = categoryRepository.getAllCategoriesStream()
    }
}
