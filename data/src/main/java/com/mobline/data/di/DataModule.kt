package com.mobline.data.di

import android.annotation.SuppressLint
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.mobline.data.errors.RemoteErrorMapper
import com.mobline.data.local.settings.AppPreferences
import com.mobline.data.local.settings.AppSettings
import com.mobline.data.local.settings.AppSettingsDataSourceImpl
import com.mobline.data.remote.currency.CurrencyApi
import com.mobline.data.remote.interceptor.DeviceInterceptor
import com.mobline.data.remote.interceptor.TokenInterceptor
import com.mobline.data.remote.interceptor.TokenInterceptorImpl
import com.mobline.data.repository.CurrencyRepositoryImpl
import com.mobline.data.repository.ErrorConverterRepositoryImpl
import com.mobline.data.repository.EventLogRepositoryImpl
import com.mobline.domain.di.ERROR_MAPPER_NETWORK
import com.mobline.domain.di.IO_CONTEXT
import com.mobline.domain.exceptions.ErrorMapper
import com.mobline.domain.repository.AppSettingsDataSource
import com.mobline.domain.repository.CurrencyRepository
import com.mobline.domain.repository.ErrorConverterRepository
import com.mobline.domain.repository.EventLogRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import kotlin.coroutines.CoroutineContext

val dataModule = module {

    single<CoroutineContext>(named(IO_CONTEXT)) { Dispatchers.IO }

    //////////////////////////////////// NETWORK ////////////////////////////////////

    single {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(
            if (getProperty("isDebug") == true.toString()) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        )
    }

    single {
        val builder = OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .addInterceptor(get<TokenInterceptor>())
            .addInterceptor(get<DeviceInterceptor>())
            .retryOnConnectionFailure(false)
            .callTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)

        if (getProperty("isDebug") == true.toString())
            builder.disableSSLVerification()

        builder.build()
    }

    single<TokenInterceptor> {
        TokenInterceptorImpl()
    }

    single {
        Firebase.analytics
    }

    single {
        DeviceInterceptor(
            osVersion = getProperty("osVersion"),
            appVersion = getProperty("version"),
            deviceModel = getProperty("deviceModel"),
            deviceID = getProperty("deviceID")
        )
    }

    single {
        Json {
            isLenient = true
            encodeDefaults = true
            ignoreUnknownKeys = true
            prettyPrint = true
        }
    }

    single<Retrofit> {
        Retrofit.Builder()
            .client(get())
            .baseUrl(getProperty("host"))
            .addConverterFactory(get<Json>().asConverterFactory("application/json".toMediaType()))
            .build()
    }

    factory<CurrencyApi> { get<Retrofit>().create(CurrencyApi::class.java) }


    //////////////////////////////////// REPOSITORY ////////////////////////////////////
    factory<CurrencyRepository> {
        CurrencyRepositoryImpl(
                api = get()
        )
    }

    factory<ErrorConverterRepository> {
        ErrorConverterRepositoryImpl(
            jsonSerializer = get<Json>()
        )
    }

    factory<AppSettingsDataSource> {
        AppSettingsDataSourceImpl(
            appSettings = get(),
        )
    }

    factory<EventLogRepository> {
        EventLogRepositoryImpl(
            firebaseAnalytics = get(),
        )
    }

    //////////////////////////////////// LOCAL ////////////////////////////////////
    factory<AppSettings> { AppPreferences(context = androidContext()) }

    //////////////////////////////////// ERROR MAPPER ////////////////////////////////////
    factory<ErrorMapper>(named(ERROR_MAPPER_NETWORK)) { RemoteErrorMapper() }
}

fun OkHttpClient.Builder.disableSSLVerification(): OkHttpClient.Builder {
    val trustAllCerts = arrayOf<TrustManager>(
        object : X509TrustManager {
            @SuppressLint("TrustAllX509TrustManager")
            override fun checkClientTrusted(
                chain: Array<X509Certificate?>?,
                authType: String?,
            ) {
            }

            @SuppressLint("TrustAllX509TrustManager")
            override fun checkServerTrusted(
                chain: Array<X509Certificate?>?,
                authType: String?,
            ) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate?>? {
                return arrayOf()
            }
        }
    )

    val sslContext = SSLContext.getInstance("SSL")
    sslContext.init(null, trustAllCerts, SecureRandom())

    return this.sslSocketFactory(sslContext.socketFactory, trustAllCerts[0] as X509TrustManager)
        .hostnameVerifier { _, _ -> true }

}