package com.survivalcoding.gangnam2kiandroidstudy.presentation

import androidx.compose.runtime.Composable
import com.survivalcoding.gangnam2kiandroidstudy.core.routing.NavigationRoot

@Composable
fun MyApp(
    deepLinkUri: String? = null,
    onDeepLinkHandled: () -> Unit = {}
) {
    NavigationRoot(
        deepLinkUri = deepLinkUri,
        onDeepLinkHandled = onDeepLinkHandled
    )
}
