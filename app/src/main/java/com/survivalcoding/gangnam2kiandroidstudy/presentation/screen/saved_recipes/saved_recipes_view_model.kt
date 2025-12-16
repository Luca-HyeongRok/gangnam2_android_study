package com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.saved_recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.survivalcoding.gangnam2kiandroidstudy.AppApplication
import com.survivalcoding.gangnam2kiandroidstudy.data.recipe.repository.RecipeRepository
import com.survivalcoding.gangnam2kiandroidstudy.model.recipe.Recipe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SavedRecipesViewModel(
    private val repository: RecipeRepository
) : ViewModel() {

    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes: StateFlow<List<Recipe>> = _recipes.asStateFlow()

    init {
        loadSavedRecipes()
    }

    private fun loadSavedRecipes() {
        viewModelScope.launch {
            _recipes.value = repository.getSavedRecipes()
        }
    }

    companion object {
        fun factory(application: AppApplication): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                SavedRecipesViewModel(application.recipeRepository)
            }
        }
    }
}
