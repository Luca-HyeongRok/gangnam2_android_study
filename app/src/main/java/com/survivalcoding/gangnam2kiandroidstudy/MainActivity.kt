package com.survivalcoding.gangnam2kiandroidstudy

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.survivalcoding.gangnam2kiandroidstudy.presentation.MyApp

class MainActivity : ComponentActivity() {

    private var deepLinkUri by mutableStateOf<String?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        this.deepLinkUri = intent.dataString

        setContent {
            MyApp(
                deepLinkUri = this.deepLinkUri,
                onDeepLinkHandled = { this.deepLinkUri = null }
            )
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        this.deepLinkUri = intent.dataString
    }
}