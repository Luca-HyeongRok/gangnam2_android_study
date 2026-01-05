package com.survivalcoding.gangnam2kiandroidstudy

import android.app.Application
import com.google.firebase.auth.FirebaseAuth
import com.survivalcoding.gangnam2kiandroidstudy.auth.AuthStateHolder
import com.survivalcoding.gangnam2kiandroidstudy.di.appModule
import com.survivalcoding.gangnam2kiandroidstudy.di.authModule
import com.survivalcoding.gangnam2kiandroidstudy.di.networkModule
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@AppApplication)
            modules(
                appModule,
                networkModule,
                authModule,
            )
        }

        val firebaseAuth: FirebaseAuth = getKoin().get()
        AuthStateHolder.init(firebaseAuth)
    }
}

