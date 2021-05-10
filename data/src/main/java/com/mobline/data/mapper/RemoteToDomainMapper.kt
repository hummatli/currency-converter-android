package com.mobline.data.mapper

import com.mobline.data.remote.currency.model.GetRatesResponseModel
import com.mobline.domain.model.currency.Currency
import com.mobline.domain.model.currency.CurrentRates
import com.mobline.domain.model.currency.Rate
import java.util.*

fun GetRatesResponseModel.toDomain() = CurrentRates(
    rates = quotes?.toList()?.map {
        val currency = Currency.valueOf(it.first.substring(3, 6))
        Rate(currency = currency, it.second)
    } ?: listOf(),
    updateDate = timestamp ?: Date().time
)
