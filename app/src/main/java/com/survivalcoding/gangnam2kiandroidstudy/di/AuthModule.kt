package com.survivalcoding.gangnam2kiandroidstudy.di

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.survivalcoding.gangnam2kiandroidstudy.BuildConfig
import com.survivalcoding.gangnam2kiandroidstudy.data.auth.AuthRepositoryImpl
import com.survivalcoding.gangnam2kiandroidstudy.domain.repository.AuthRepository
import com.survivalcoding.gangnam2kiandroidstudy.presentation.auth.GoogleAuthUiClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val authModule = module {

    single<FirebaseAuth> {
        val firebaseAuth = Firebase.auth

        // Firebase Auth Emulator
        // - DEBUG 빌드에서만 사용
        // - Android Emulator 기준: 10.0.2.2
        if (BuildConfig.DEBUG) {
            firebaseAuth.useEmulator("10.0.2.2", 9099)
        }

        firebaseAuth
    }

    single {
        // Application Context 사용
        // ※ Credential Manager UI 표시 시 Activity context가 더 적합할 수 있음
        //   (현재 구조에서는 안정성을 위해 유지)
        GoogleAuthUiClient(
            appContext = androidContext()
        )
    }

    single<AuthRepository> {
        AuthRepositoryImpl(get())
    }
}
