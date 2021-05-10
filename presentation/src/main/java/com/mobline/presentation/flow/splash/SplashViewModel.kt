package com.mobline.presentation.flow.splash

import com.mobline.domain.usecase.logs.LogScreenInfoUseCase
import com.mobline.domain.usecase.splash.SplashStatus
import com.mobline.domain.usecase.splash.SplashUseCase
import com.mobline.presentation.base.BaseViewModel

class SplashViewModel(
    splashUseCase: SplashUseCase,
    logScreenInfoUseCase: LogScreenInfoUseCase,
) : BaseViewModel<SplashState, Nothing>(logScreenInfoUseCase) {

    init {
        launchAll(loadingHandle = {}) {
            when (splashUseCase.getWith(Unit)) {
                is SplashStatus.Registered -> {
                    postState(SplashState.ProceedWithAuthorization)
                }
                is SplashStatus.NotRegistered -> {
                    postState(SplashState.ProceedWithOnboarding)
                }
            }
        }
    }
}

sealed class SplashState {
    object ProceedWithOnboarding : SplashState()
    object ProceedWithAuthorization : SplashState()
}