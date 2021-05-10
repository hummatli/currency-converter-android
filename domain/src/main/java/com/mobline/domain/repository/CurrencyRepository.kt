package com.mobline.domain.repository

import com.mobline.domain.model.currency.CurrentRates

interface CurrencyRepository {
    suspend fun getRates(): CurrentRates
}