package com.mobline.domain.usecase.currency

import com.mobline.domain.base.BaseUseCase
import com.mobline.domain.exceptions.ErrorConverter
import com.mobline.domain.model.currency.CurrentRates
import com.mobline.domain.model.currency.Rate
import com.mobline.domain.repository.CurrencyRepository
import kotlin.coroutines.CoroutineContext

class GetRatesUseCase(
    context: CoroutineContext,
    mapper: ErrorConverter,
    private val repository: CurrencyRepository,
) : BaseUseCase<Unit, CurrentRates>(context, mapper) {

    override suspend fun executeOnBackground(params: Unit): CurrentRates {
        return repository.getRates()
    }
}