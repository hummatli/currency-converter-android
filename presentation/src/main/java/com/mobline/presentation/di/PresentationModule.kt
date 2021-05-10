package com.mobline.presentation.di

import com.mobline.presentation.base.LanguageContextWrapper
import com.mobline.presentation.flow.main.MainViewModel
import com.mobline.presentation.flow.main.content.MainPageViewModel
import com.mobline.presentation.flow.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    viewModel {
        SplashViewModel(
                splashUseCase = get(),
                logScreenInfoUseCase = get(),
        )
    }

    viewModel {
        MainPageViewModel(
                getRatesUseCase = get(),
                logScreenInfoUseCase = get(),
        )
    }

    viewModel {
        MainViewModel(
                logScreenInfoUseCase = get()
        )
    }

    factory { LanguageContextWrapper(dataSource = get()) }
}