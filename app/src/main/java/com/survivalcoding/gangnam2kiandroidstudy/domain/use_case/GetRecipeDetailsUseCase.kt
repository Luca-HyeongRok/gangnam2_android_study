package com.survivalcoding.gangnam2kiandroidstudy.domain.use_case

import com.survivalcoding.gangnam2kiandroidstudy.domain.repository.RecipeRepository
import com.survivalcoding.gangnam2kiandroidstudy.domain.model.RecipeDetails
import com.survivalcoding.gangnam2kiandroidstudy.domain.repository.IngredientRepository
import com.survivalcoding.gangnam2kiandroidstudy.domain.repository.ProcedureRepository

class GetRecipeDetailsUseCase(
    private val recipeRepository: RecipeRepository,
    private val ingredientRepository: IngredientRepository,
    private val procedureRepository: ProcedureRepository,
) {
    suspend operator fun invoke(recipeId: Int): RecipeDetails? {
        val recipe = recipeRepository.getRecipeById(recipeId)
            ?: return null

        val ingredients = ingredientRepository.getIngredients(recipeId)
        val procedures = procedureRepository.getProcedures(recipeId)

        return RecipeDetails(
            recipe = recipe,
            ingredients = ingredients,
            procedures = procedures
        )
    }
}

