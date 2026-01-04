package com.survivalcoding.gangnam2kiandroidstudy.di

import com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.sign_in.SignInViewModel
import com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.sign_up.SignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SignInViewModel(get()) }
    viewModel { SignUpViewModel(get()) }
}
