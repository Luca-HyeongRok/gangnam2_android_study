package com.survivalcoding.gangnam2kiandroidstudy.di

import com.survivalcoding.gangnam2kiandroidstudy.data.recipe.datasource.RecipeDataSourceImpl
import com.survivalcoding.gangnam2kiandroidstudy.data.recipe.repository.BookmarkRepositoryImpl
import com.survivalcoding.gangnam2kiandroidstudy.data.recipe.repository.RecipeRepository
import com.survivalcoding.gangnam2kiandroidstudy.data.recipe.repository.RecipeRepositoryImpl
import com.survivalcoding.gangnam2kiandroidstudy.data.recipe.repository.SavedRecipesRepositoryImpl
import com.survivalcoding.gangnam2kiandroidstudy.domain.repository.BookmarkRepository
import com.survivalcoding.gangnam2kiandroidstudy.domain.repository.SavedRecipesRepository
import com.survivalcoding.gangnam2kiandroidstudy.domain.use_case.GetSavedRecipesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRecipeDataSource(): RecipeDataSourceImpl {
        return RecipeDataSourceImpl()
    }
    @Provides
    @Singleton
    fun provideRecipeRepository(
        dataSource: RecipeDataSourceImpl
    ): RecipeRepository {
        return RecipeRepositoryImpl(dataSource)
    }

    @Provides
    @Singleton
    fun provideBookmarkRepository(
        recipeRepository: RecipeRepository
    ): BookmarkRepository {
        return BookmarkRepositoryImpl(recipeRepository)
    }

    @Provides
    @Singleton
    fun provideSavedRecipesRepository(
        recipeRepository: RecipeRepository
    ): SavedRecipesRepository {
        return SavedRecipesRepositoryImpl(recipeRepository)
    }
    @Provides
    @Singleton
    fun provideGetSavedRecipesUseCase(
        bookmarkRepository: BookmarkRepository,
        savedRecipesRepository: SavedRecipesRepository
    ): GetSavedRecipesUseCase {
        return GetSavedRecipesUseCase(
            bookmarkRepository,
            savedRecipesRepository
        )
    }
}
