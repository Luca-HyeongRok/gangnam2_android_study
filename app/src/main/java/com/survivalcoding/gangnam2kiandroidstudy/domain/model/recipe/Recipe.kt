package com.survivalcoding.gangnam2kiandroidstudy.domain.model.recipe

data class Recipe(
    val id: Int,
    val category: String,
    val name: String,
    val imageUrl: String,
    val chef: String,
    val time: String,
    val rating: Double,
    val createdAt: Long,
    val isSaved: Boolean = false,
)
