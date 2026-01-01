package com.survivalcoding.gangnam2kiandroidstudy.di

import android.content.Context
import android.net.ConnectivityManager
import com.survivalcoding.gangnam2kiandroidstudy.data.recipe.datasource.ProcedureDataSource
import com.survivalcoding.gangnam2kiandroidstudy.data.recipe.datasource.ProcedureDataSourceImpl
import com.survivalcoding.gangnam2kiandroidstudy.data.recipe.datasource.RecipeDataSource
import com.survivalcoding.gangnam2kiandroidstudy.data.recipe.datasource.RecipeDataSourceImpl
import com.survivalcoding.gangnam2kiandroidstudy.data.recipe.network.NetworkMonitorImpl
import com.survivalcoding.gangnam2kiandroidstudy.data.recipe.repository.ClipBoardRepositoryImpl
import com.survivalcoding.gangnam2kiandroidstudy.data.recipe.repository.IngredientRepositoryImpl
import com.survivalcoding.gangnam2kiandroidstudy.data.recipe.repository.ProcedureRepositoryImpl
import com.survivalcoding.gangnam2kiandroidstudy.domain.repository.RecipeRepository
import com.survivalcoding.gangnam2kiandroidstudy.data.recipe.repository.RecipeRepositoryImpl
import com.survivalcoding.gangnam2kiandroidstudy.data.recipe.repository.SavedRecipesRepositoryImpl
import com.survivalcoding.gangnam2kiandroidstudy.domain.network.NetworkMonitor
import com.survivalcoding.gangnam2kiandroidstudy.domain.repository.ClipBoardRepository
import com.survivalcoding.gangnam2kiandroidstudy.domain.repository.IngredientRepository
import com.survivalcoding.gangnam2kiandroidstudy.domain.repository.ProcedureRepository
import com.survivalcoding.gangnam2kiandroidstudy.domain.repository.SavedRecipesRepository
import com.survivalcoding.gangnam2kiandroidstudy.domain.use_case.CopyLinkUseCase
import com.survivalcoding.gangnam2kiandroidstudy.domain.use_case.GetRecipeDetailsUseCase
import com.survivalcoding.gangnam2kiandroidstudy.domain.use_case.GetSavedRecipesUseCase
import com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.detail.RecipeDetailViewModel
import com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.home.HomeViewModel
import com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.saved_recipes.SavedRecipesViewModel
import com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.search_recipe.SearchRecipeViewModel
import com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.splash.SplashViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    includes(bookmarkModule) // Include the new bookmark module

    // Network
    single {
        androidContext()
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    single<NetworkMonitor> {
        NetworkMonitorImpl(get())
    }

    // DataSource
    single<RecipeDataSource> { RecipeDataSourceImpl() }
    single<ProcedureDataSource> { ProcedureDataSourceImpl() }


    // Repository
    single<RecipeRepository> { RecipeRepositoryImpl(get()) }
    single<SavedRecipesRepository> { SavedRecipesRepositoryImpl(get()) }
    single<IngredientRepository> { IngredientRepositoryImpl(get()) }
    single<ProcedureRepository> { ProcedureRepositoryImpl(get()) }

    // UseCase
    single {
        GetSavedRecipesUseCase(
            bookmarkRepository = get(),
            savedRecipesRepository = get(),
        )
    }
    single {
        GetRecipeDetailsUseCase(
            recipeRepository = get(),
            ingredientRepository = get(),
            procedureRepository = get()
        )
    }
    single {
        CopyLinkUseCase(
            clipBoardRepository = get()
        )
    }

    // Clipboard
    single<ClipBoardRepository> {
        ClipBoardRepositoryImpl(
            context = androidContext()
        )
    }

    // ViewModel
    viewModel {
        HomeViewModel(
            repository = get(),
            addBookmarkUseCase = get(),
            removeBookmarkUseCase = get(),
            getBookmarkedRecipeIdsUseCase = get(),
        )
    }

    viewModel {
        SearchRecipeViewModel(repository = get())
    }

    viewModel {
        SavedRecipesViewModel(
            getSavedRecipesUseCase = get(),
            bookmarkRepository = get(),
        )
    }

    viewModel {
        SplashViewModel(
            networkMonitor = get()
        )
    }

    viewModel {
        RecipeDetailViewModel(
            getRecipeDetailsUseCase = get(),
            copyLinkUseCase = get()
        )
    }

}

