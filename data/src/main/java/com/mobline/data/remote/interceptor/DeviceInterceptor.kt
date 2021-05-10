package com.mobline.data.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class DeviceInterceptor(
    private val osVersion: String,
    private val appVersion: String,
    private val deviceModel: String,
    private val deviceID: String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder =
            chain.request().newBuilder()
                .addHeader("Device-Model", deviceModel)
                .addHeader("Device-OS-Type", "android")
                .addHeader("Device-OS-Version", osVersion)
                .addHeader("App-Version", appVersion)
                .addHeader("Device-ID", deviceID)

        val request = requestBuilder.build()

        return chain.proceed(request)
    }
}