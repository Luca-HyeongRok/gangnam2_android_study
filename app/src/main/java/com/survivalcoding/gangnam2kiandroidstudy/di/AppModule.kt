package com.survivalcoding.gangnam2kiandroidstudy.di

import android.content.Context
import android.net.ConnectivityManager
import com.survivalcoding.gangnam2kiandroidstudy.data.auth.AuthRepositoryImpl
import com.survivalcoding.gangnam2kiandroidstudy.data.recipe.datasource.ProcedureDataSource
import com.survivalcoding.gangnam2kiandroidstudy.data.recipe.datasource.ProcedureDataSourceImpl
import com.survivalcoding.gangnam2kiandroidstudy.data.recipe.datasource.RecipeDataSource
import com.survivalcoding.gangnam2kiandroidstudy.data.recipe.datasource.RecipeDataSourceImpl
import com.survivalcoding.gangnam2kiandroidstudy.data.recipe.network.NetworkMonitorImpl
import com.survivalcoding.gangnam2kiandroidstudy.data.recipe.repository.*
import com.survivalcoding.gangnam2kiandroidstudy.domain.network.NetworkMonitor
import com.survivalcoding.gangnam2kiandroidstudy.domain.repository.*
import com.survivalcoding.gangnam2kiandroidstudy.domain.use_case.CopyLinkUseCase
import com.survivalcoding.gangnam2kiandroidstudy.domain.use_case.GetRecipeDetailsUseCase
import com.survivalcoding.gangnam2kiandroidstudy.domain.use_case.GetSavedRecipesUseCase
import com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.auth.AuthViewModel
import com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.detail.RecipeDetailViewModel
import com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.home.HomeViewModel
import com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.saved_recipes.SavedRecipesViewModel
import com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.saved_recipes_legacy.SavedRecipesLegacyViewModel
import com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.search_recipe.SearchRecipeViewModel
import com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.splash.SplashViewModel
import com.google.firebase.auth.FirebaseAuth
import com.survivalcoding.gangnam2kiandroidstudy.domain.use_case.AddBookmarkUseCase
import com.survivalcoding.gangnam2kiandroidstudy.domain.use_case.GetBookmarkedRecipeIdsUseCase
import com.survivalcoding.gangnam2kiandroidstudy.domain.use_case.RemoveBookmarkUseCase
import com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.sign_in.SignInViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    /* -----------------------------
     * Android System
     * ----------------------------- */
    single {
        androidContext()
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    /* -----------------------------
     * Network
     * ----------------------------- */
    single<NetworkMonitor> {
        NetworkMonitorImpl(get())
    }

    /* -----------------------------
     * Firebase
     * ----------------------------- */
    single {
        FirebaseAuth.getInstance()
    }

    /* -----------------------------
     * Auth
     * ----------------------------- */
    single<AuthRepository> {
        AuthRepositoryImpl(get())
    }

//    single {
//        GoogleAuthUiClient(
//            context = androidContext()
//        )
//    }

    /* -----------------------------
     * DataSource
     * ----------------------------- */
    single<RecipeDataSource> { RecipeDataSourceImpl() }
    single<ProcedureDataSource> { ProcedureDataSourceImpl() }

    /* -----------------------------
     * Repository
     * ----------------------------- */
    single<RecipeRepository> { RecipeRepositoryImpl(get()) }
    single<SavedRecipesRepository> { SavedRecipesRepositoryImpl(get()) }
    single<IngredientRepository> { IngredientRepositoryImpl(get()) }
    single<ProcedureRepository> { ProcedureRepositoryImpl(get()) }
    single<ClipBoardRepository> {
        ClipBoardRepositoryImpl(
            context = androidContext()
        )
    }

    /* -----------------------------
     * UseCase
     * ----------------------------- */
    single {
        GetSavedRecipesUseCase(
            bookmarkRepository = get(),
            savedRecipesRepository = get()
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

    single {
        AddBookmarkUseCase(
            bookmarkRepository = get()
        )
    }

    single {
        RemoveBookmarkUseCase(
            bookmarkRepository = get()
        )
    }

    single {
        GetBookmarkedRecipeIdsUseCase(
            bookmarkRepository = get()
        )
    }

    /* -----------------------------
     * ViewModel
     * ----------------------------- */
    viewModel {
        HomeViewModel(
            repository = get(),
            addBookmarkUseCase = get(),
            removeBookmarkUseCase = get(),
            getBookmarkedRecipeIdsUseCase = get()
        )
    }

    viewModel {
        SearchRecipeViewModel(
            repository = get()
        )
    }

    viewModel {
        SavedRecipesViewModel(
            getSavedRecipesUseCase = get(),
            bookmarkRepository = get()
        )
    }
    viewModel {
        SavedRecipesLegacyViewModel(
            getSavedRecipesUseCase = get(),
            bookmarkRepository = get()
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

    viewModel {
        AuthViewModel(
            authRepository = get(),
        )
    }
    viewModel {
        SignInViewModel(
            authRepository = get(),
        )
    }
}
