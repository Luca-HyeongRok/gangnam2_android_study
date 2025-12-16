package com.survivalcoding.gangnam2kiandroidstudy

import android.app.Application
import com.survivalcoding.gangnam2kiandroidstudy.data.recipe.datasource.RecipeDataSourceImpl
import com.survivalcoding.gangnam2kiandroidstudy.data.recipe.repository.RecipeRepository
import com.survivalcoding.gangnam2kiandroidstudy.data.recipe.repository.RecipeRepositoryImpl

class AppApplication : Application() {

    val recipeRepository: RecipeRepository by lazy {
        RecipeRepositoryImpl(
            dataSource = RecipeDataSourceImpl()
        )
    }
}
