package com.mobline.data.remote.currency

import com.mobline.data.remote.currency.model.GetRatesResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {

    @GET("/live")
    suspend fun getRates(
            @Query("access_key") accessKey: String,
            @Query("source") source: String? = null,
            @Query("currencies") currencies: String,
            @Query("format") format: Int = 1
    ): GetRatesResponseModel
}