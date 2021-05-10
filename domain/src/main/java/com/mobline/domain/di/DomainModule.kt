 package com.mobline.domain.di

import com.mobline.domain.exceptions.ErrorConverter
import com.mobline.domain.exceptions.ErrorConverterImpl
import com.mobline.domain.usecase.currency.GetRatesUseCase
import com.mobline.domain.usecase.logs.LogScreenInfoUseCase
import com.mobline.domain.usecase.splash.SplashUseCase
import org.koin.core.qualifier.named
import org.koin.dsl.module


const val IO_CONTEXT = "IO_CONTEXT"
const val ERROR_MAPPER_NETWORK = "ERROR_MAPPER_NETWORK"

val domainModule = module {
    single<ErrorConverter> {
        ErrorConverterImpl(
            setOf(
                get(named(ERROR_MAPPER_NETWORK))
            )
        )
    }

    factory {
        GetRatesUseCase(
                context = get(named(IO_CONTEXT)),
                mapper = get(),
                repository = get(),
        )
    }

    factory {
        LogScreenInfoUseCase(
            context = get(named(IO_CONTEXT)),
            converter = get(),
            repository = get(),
        )
    }

    factory {
        SplashUseCase(
            context = get(named(IO_CONTEXT)),
            converter = get()
        )
    }
}
