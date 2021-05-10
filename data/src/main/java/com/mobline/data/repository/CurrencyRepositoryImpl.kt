package com.mobline.data.repository

import com.mobline.data.constants.DataConstants
import com.mobline.data.mapper.toDomain
import com.mobline.data.remote.currency.CurrencyApi
import com.mobline.domain.model.currency.Currency
import com.mobline.domain.model.currency.CurrentRates
import com.mobline.domain.repository.CurrencyRepository

class CurrencyRepositoryImpl(
    private val api: CurrencyApi
) : CurrencyRepository {

    override suspend fun getRates(): CurrentRates {
        return api.getRates(
            accessKey = DataConstants.ACCESS_KEY,
            source = Currency.USD.name,
            currencies = DataConstants.CURRENCY_LIST.joinToString(",")
        ).toDomain()
    }
}