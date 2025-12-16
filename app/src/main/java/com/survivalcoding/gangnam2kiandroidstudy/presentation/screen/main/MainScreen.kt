package com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.survivalcoding.gangnam2kiandroidstudy.R
import com.survivalcoding.gangnam2kiandroidstudy.core.routing.Route
import com.survivalcoding.gangnam2kiandroidstudy.ui.theme.AppColors

@Composable
fun MainScreen(
    body: @Composable (modifier: Modifier) -> Unit,
    backStack: NavBackStack<NavKey>,
    modifier: Modifier = Modifier,
) {
    val currentRoute = backStack.lastOrNull()

    fun switchTab(route: NavKey) {
        backStack.clear()
        backStack.add(route)
    }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = AppColors.white,
                contentColor = AppColors.gray4
            ) {

                NavigationBarItem(
                    selected = currentRoute is Route.Home,
                    onClick = { switchTab(Route.Home) },
                    icon = {
                        Icon(
                            painter = if (currentRoute is Route.Home)
                                painterResource(R.drawable.ic_bottom_home_selected)
                            else
                                painterResource(R.drawable.ic_bottom_home_unselected),
                            contentDescription = "Home",
                            tint = Color.Unspecified
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = Color.Transparent
                    )
                )

                NavigationBarItem(
                    selected = currentRoute is Route.SavedRecipes,
                    onClick = { switchTab(Route.SavedRecipes) },
                    icon = {
                        Icon(
                            painter = if (currentRoute is Route.SavedRecipes)
                                painterResource(R.drawable.ic_bottom_saved_selected)
                            else
                                painterResource(R.drawable.ic_bottom_saved_unselected),
                            contentDescription = "Saved",
                            tint = Color.Unspecified
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = Color.Transparent
                    )
                )

                NavigationBarItem(
                    selected = currentRoute is Route.Notifications,
                    onClick = { switchTab(Route.Notifications) },
                    icon = {
                        Icon(
                            painter = if (currentRoute is Route.Notifications)
                                painterResource(R.drawable.ic_bottom_notification_selected)
                            else
                                painterResource(R.drawable.ic_bottom_notification_unselected),
                            contentDescription = "Notifications",
                            tint = Color.Unspecified
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = Color.Transparent
                    )
                )

                NavigationBarItem(
                    selected = currentRoute is Route.Profile,
                    onClick = { switchTab(Route.Profile) },
                    icon = {
                        Icon(
                            painter = if (currentRoute is Route.Profile)
                                painterResource(R.drawable.ic_bottom_profile_selected)
                            else
                                painterResource(R.drawable.ic_bottom_profile_unselected),
                            contentDescription = "Profile",
                            tint = Color.Unspecified
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = Color.Transparent
                    )
                )
            }
        }
    ) { innerPadding ->
        body(modifier.padding(innerPadding))
    }
}
