package com.survivalcoding.gangnam2kiandroidstudy.data.recipe.mapper

import com.survivalcoding.gangnam2kiandroidstudy.data.recipe.dto.RecipeDTO
import com.survivalcoding.gangnam2kiandroidstudy.model.recipe.Recipe

fun RecipeDTO.toModel(): Recipe =
    Recipe(
        id = this.id?:0,
        category = this.category?:"",
        name = this.name?:"Unknown Title",
        imageUrl = this.image?:"",
        chef = this.chef?:"Unknown Chef",
        time = this.time ?:"",
        rating = this.rating ?: 0.0,
        createdAt = this.createdAt?: System.currentTimeMillis(),
        isSaved = this.isSaved,
    )
