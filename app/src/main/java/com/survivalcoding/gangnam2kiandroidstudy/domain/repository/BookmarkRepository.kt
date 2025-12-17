package com.survivalcoding.gangnam2kiandroidstudy.domain.repository

interface BookmarkRepository {
    suspend fun getSavedRecipeIds(): List<Int>
    suspend fun removeSavedRecipeId(id:Int)
}
