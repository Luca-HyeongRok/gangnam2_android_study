package com.survivalcoding.gangnam2kiandroidstudy.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.survivalcoding.gangnam2kiandroidstudy.BuildConfig
import com.survivalcoding.gangnam2kiandroidstudy.data.auth.AuthRepositoryImpl
import com.survivalcoding.gangnam2kiandroidstudy.domain.repository.AuthRepository
import com.survivalcoding.gangnam2kiandroidstudy.presentation.auth.GoogleAuthUiClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val authModule = module {

    single<FirebaseAuth> {
        val firebaseAuth = Firebase.auth
        if (BuildConfig.FLAVOR == "dev" || BuildConfig.FLAVOR == "qa") {
            firebaseAuth.useEmulator("10.0.2.2", 9099)
        }
        firebaseAuth
    }

    single {
        GoogleAuthUiClient(
            context = androidContext(),
        )
    }

    single<AuthRepository> {
        AuthRepositoryImpl(get())
    }
}
