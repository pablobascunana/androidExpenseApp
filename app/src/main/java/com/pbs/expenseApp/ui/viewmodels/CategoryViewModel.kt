package com.pbs.expenseApp.ui.viewmodels

import android.content.Context
import androidx.compose.ui.res.stringResource
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

    private val appContext = context

    private val defaultCategories: MutableList<Category> = mutableListOf(
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

    private val _categories = MutableLiveData(defaultCategories)
    val categories: LiveData<List<Category>>
        get() = _categories

    private val _expandedCategoryTypeDropDown = MutableLiveData(false)
    val expandedCategoryTypeDropDown: LiveData<Boolean>
        get() = _expandedCategoryTypeDropDown

    private val _categoryName = MutableLiveData("")
    val categoryName: LiveData<String>
        get() = _categoryName

    private val _categoryType = MutableLiveData("")
    val categoryType: LiveData<String>
        get() = _categoryType

    private val _canSaveCategory = MutableLiveData(false)
    val canSaveCategory: LiveData<Boolean>
        get() = _canSaveCategory

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
            type = type
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
    private suspend fun getCategories() {
        _categories.value = categoryRepository.getAllCategoriesStream()
    }

    fun isExpandedCategoryTypeDropdown() {
        _expandedCategoryTypeDropDown.value = !_expandedCategoryTypeDropDown.value!!
    }

    fun setExpandedCategoryTypeDropdown(value: Boolean) {
        _expandedCategoryTypeDropDown.value = value
    }

    fun setCategoryName(text: String) {
        _categoryName.value = text
    }
    fun setCategoryType(text: String) {
        _categoryType.value = text
    }

    fun canSaveCategory() {
        _canSaveCategory.value = !_canSaveCategory.value!!
    }

}
