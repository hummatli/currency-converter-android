package com.mobline.presentation.flow.splash

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mobline.currencyconverter.presentation.databinding.FragmentSplashBinding
import com.mobline.presentation.base.BaseFragment
import kotlin.reflect.KClass

class SplashFragment :
    BaseFragment<SplashState, Nothing, SplashViewModel, FragmentSplashBinding>() {

    override val vmClazz: KClass<SplashViewModel>
        get() = SplashViewModel::class
    override val bindingCallback: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSplashBinding
        get() = FragmentSplashBinding::inflate
    override val screenName: String
        get() = "splash"

    override fun observeState(state: SplashState) {
        when (state) {
            is SplashState.ProceedWithAuthorization -> proceedWithAuthorization()
            SplashState.ProceedWithOnboarding -> proceedWithOnboarding()
        }
    }

    private fun proceedWithOnboarding() {
        val direction = SplashFragmentDirections.actionSplashFragmentToMainPageFragment()
        viewModel.navigate(direction)
    }

    private fun proceedWithAuthorization() {
        navigateToMain()
    }

    private fun navigateToMain() {
        toMain()
    }
}