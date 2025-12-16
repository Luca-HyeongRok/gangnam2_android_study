package com.survivalcoding.gangnam2kiandroidstudy.data.recipe.dto

import kotlinx.serialization.Serializable

@Serializable
data class RecipesResponseDTO(
    val recipes: List<RecipeDTO>
)

@Serializable
data class RecipeDTO(
    val category: String?,
    val id: Int?,
    val name: String?,
    val image: String?,
    val chef: String?,
    val time: String?,
    val rating: Double?,
    val ingredients: List<RecipeIngredientDTO>?,
    val createdAt: Long? = System.currentTimeMillis(),
    val isSaved:Boolean = false,
)

@Serializable
data class RecipeIngredientDTO(
    val ingredient: IngredientDTO?,
    val amount: Int?,
)

@Serializable
data class IngredientDTO(
    val id: Int?,
    val name: String?,
    val image: String?,
)
