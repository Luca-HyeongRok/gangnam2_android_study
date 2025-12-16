package com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.search_recipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.survivalcoding.gangnam2kiandroidstudy.AppApplication
import com.survivalcoding.gangnam2kiandroidstudy.data.recipe.repository.RecipeRepository
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.bottomsheet.CategoryFilter
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.bottomsheet.TimeFilter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchRecipeViewModel(
    private val repository: RecipeRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(SearchRecipeState())
    val state: StateFlow<SearchRecipeState> = _state.asStateFlow()

    init {
        loadRecipes()
    }

    private fun loadRecipes() {
        viewModelScope.launch {
            val recipes = repository.getRecipes()
            _state.value = _state.value.copy(
                allRecipes = recipes,
                filteredRecipes = recipes,
                filteredRecipesText = "${recipes.size} results"
            )
        }
    }

    fun updateSearchQuery(keyword: String) {
        _state.value = _state.value.copy(searchKeyword = keyword)
        applyFilter()
    }

    fun updateFilterSearchState(newState: FilterSearchState) {
        _state.value = _state.value.copy(filterState = newState)
    }

    fun applyFilter() {
        val current = _state.value
        val filter = current.filterState

        var filtered = current.allRecipes

        // SearchKeyword
        val keyword = current.searchKeyword.trim()
        if (keyword.isNotEmpty()) {
            filtered = filtered.filter { it.name.contains(keyword, ignoreCase = true) }
        }

        // TimeFilter
        filtered = when (filter.time) {
            TimeFilter.NEWEST -> filtered.sortedByDescending { it.createdAt }
            TimeFilter.OLDEST -> filtered.sortedBy { it.createdAt }
            TimeFilter.POPULARITY -> filtered.sortedByDescending { it.rating }
            else -> filtered
        }

        // RateFilter (label이 숫자일 때만)
        filter.rate?.let { rateFilter ->
            val minRating = rateFilter.label.toFloatOrNull()
            if (minRating != null) {
                filtered = filtered.filter { it.rating >= minRating }
            }
        }

        // CategoryFilter
        filter.category?.let { categoryFilter ->
            if (categoryFilter != CategoryFilter.ALL) {
                filtered = filtered.filter { it.category == categoryFilter.label }
            }
        }

        _state.value = current.copy(
            filteredRecipes = filtered,
            filteredRecipesText = "${filtered.size} results"
        )
    }

    companion object {
        fun factory(application: AppApplication): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                SearchRecipeViewModel(application.recipeRepository)
            }
        }
    }
}
