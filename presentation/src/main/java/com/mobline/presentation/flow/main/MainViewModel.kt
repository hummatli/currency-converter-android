package com.mobline.presentation.flow.main

import com.mobline.domain.usecase.logs.LogScreenInfoUseCase
import com.mobline.presentation.base.BaseViewModel

class MainViewModel(
    logScreenInfoUseCase: LogScreenInfoUseCase
) : BaseViewModel<Nothing, Nothing>(logScreenInfoUseCase)