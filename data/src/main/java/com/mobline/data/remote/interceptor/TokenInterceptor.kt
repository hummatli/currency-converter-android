package com.mobline.data.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response


interface TokenInterceptor :  Interceptor {
    fun setTokens(accessToken: String, refreshToken: String)
    fun removeTokens()
}

class TokenInterceptorImpl : TokenInterceptor {
    private var accessToken: String? = null
    private var refreshToken: String? = null

    override fun setTokens(accessToken: String, refreshToken: String) {
        this.accessToken = accessToken
        this.refreshToken = refreshToken
    }

    override fun removeTokens() {
        accessToken = null
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        accessToken?.let {
            builder.addHeader("dp-access-token", it)
        }
        return chain.proceed(builder.build())
    }
}