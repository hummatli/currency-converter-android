package com.mobline.data.remote.currency.model

import kotlinx.serialization.Serializable

@Serializable
class GetRatesResponseModel(
    val success: Boolean,
    val terms: String? = null,
    val privacy: String? = null,
    val timestamp: Long? = null,
    val source: String,
    val quotes: Map<String, Double>? = null,
    val error: GetRatesErrorModel? = null
)