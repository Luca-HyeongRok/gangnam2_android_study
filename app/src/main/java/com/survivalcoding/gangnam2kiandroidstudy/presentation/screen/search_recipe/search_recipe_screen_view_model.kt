package com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.search_recipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.survivalcoding.gangnam2kiandroidstudy.data.recipe.repository.RecipeRepository
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.bottomsheet.CategoryFilter
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.bottomsheet.TimeFilter
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchRecipeViewModel(
    private val repository: RecipeRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(SearchRecipeState())
    val state: StateFlow<SearchRecipeState> = _state.asStateFlow()

    private val _event = MutableSharedFlow<SearchRecipeEvent>()
    val event = _event.asSharedFlow()


    fun onAction(action: SearchRecipeAction) {
        when (action) {

            SearchRecipeAction.Load -> {
                loadRecipes()
            }

            is SearchRecipeAction.InputKeyword -> {
                updateSearchQuery(action.keyword)
            }

            SearchRecipeAction.ApplyFilter -> {
                applyFilter()
                emitSnackBar("필터가 적용되었습니다.")
            }

            is SearchRecipeAction.CancelFilter -> {
                _state.value = _state.value.copy(
                    filterState = action.filterState
                )
                applyFilter()
                emitSnackBar("필터가 취소되었습니다.")
            }

            else -> Unit
        }
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

        // 검색어
        val keyword = current.searchKeyword.trim()
        if (keyword.isNotEmpty()) {
            filtered = filtered.filter {
                it.name.contains(keyword, ignoreCase = true)
            }
        }

        // 시간 필터
        filtered = when (filter.time) {
            TimeFilter.NEWEST -> filtered.sortedByDescending { it.createdAt }
            TimeFilter.OLDEST -> filtered.sortedBy { it.createdAt }
            TimeFilter.POPULARITY -> filtered.sortedByDescending { it.rating }
            else -> filtered
        }

        // 평점 필터
        filter.rate?.label?.toFloatOrNull()?.let { min ->
            filtered = filtered.filter { it.rating >= min }
        }

        // 카테고리 필터
        filter.category?.let {
            if (it != CategoryFilter.ALL) {
                filtered = filtered.filter { recipe ->
                    recipe.category == it.label
                }
            }
        }

        _state.value = current.copy(
            filteredRecipes = filtered,
            filteredRecipesText = "${filtered.size} results"
        )
    }

    private fun emitSnackBar(message : String){
        viewModelScope.launch {
            _event.emit(SearchRecipeEvent.ShowSnackBar(message))
        }
    }

}
