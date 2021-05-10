package com.mobline.data.remote.currency.model

import kotlinx.serialization.Serializable

@Serializable
class GetRatesErrorModel(
    val code: Int,
    val info: String
)