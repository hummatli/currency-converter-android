package com.mobline.data.constants

import com.mobline.domain.model.currency.Currency

class DataConstants {

    companion object {
        const val ACCESS_KEY = "7934d2d1b935608b8cd6cb9c953598f2"
        val CURRENCY_LIST = arrayListOf(
            Currency.AED,
            Currency.AUD,
            Currency.AZN,
            Currency.CAD,
            Currency.CNY,
            Currency.EUR,
            Currency.GBP,
            Currency.USD,
            Currency.INR,
            Currency.JPY,
            Currency.MXN,
            Currency.PLN,
            Currency.RUB,
            Currency.TRY,
        )
    }
}