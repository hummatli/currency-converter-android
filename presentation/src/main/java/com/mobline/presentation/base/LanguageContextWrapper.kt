package com.mobline.presentation.base

import android.content.Context
import android.content.res.Configuration
import com.mobline.domain.repository.AppSettingsDataSource
import java.util.*


class LanguageContextWrapper(private val dataSource: AppSettingsDataSource) {

    fun applyLanguage(c: Context): Context = updateResources(c, dataSource.getAppLanguage().name)

    private fun updateResources(context: Context, language: String): Context {
        val config = Configuration(context.resources.configuration)
        config.setLocale(Locale(language.toLowerCase(Locale.getDefault())))
        return context.createConfigurationContext(config)
    }
}
