package com.mobline.domain.model.currency


enum class Currency { AED, AUD, AZN, CAD, CNY, EUR, GBP, USD, INR, JPY, KRW, MXN, PLN, RUB, TRY }

//Value is calculated based on USD
data class Rate(
    val currency: Currency,
    val value: Double
)

data class RateExpanded(
    val cFrom: Currency,
    val cTo: Currency,
    val value: Double
)