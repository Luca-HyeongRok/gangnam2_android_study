package com.survivalcoding.gangnam2kiandroidstudy.core.routing

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route : NavKey {
    @Serializable
    data object Splash : Route

    @Serializable
    data object SignIn : Route

    @Serializable
    data object SignUp : Route

    // data object에서 data class로 변경하여 시작 탭 정보를 담을 수 있도록 함
    @Serializable
    data class Main(val startTab: Route = Home) : Route

    @Serializable
    data object Home : Route

    @Serializable
    data object SavedRecipes : Route

    @Serializable
    data object SearchRecipes : Route

    @Serializable
    data object Notifications : Route

    @Serializable
    data object Profile : Route

    @Serializable
    data class RecipeDetail(val recipeId: Int) : Route
}