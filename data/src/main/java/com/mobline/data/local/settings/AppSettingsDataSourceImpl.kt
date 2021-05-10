package com.mobline.data.local.settings

import com.mobline.domain.constant.AppLanguage
import com.mobline.domain.repository.AppSettingsDataSource
import kotlinx.coroutines.flow.Flow

class AppSettingsDataSourceImpl(
    private val appSettings: AppSettings
) : AppSettingsDataSource {

    override fun getAppLanguage(): AppLanguage = appSettings.getAppLanguage()

    override fun setAppLanguage(langCode: AppLanguage) {
        appSettings.setAppLanguage(langCode)
    }

    override fun observeLanguage(): Flow<AppLanguage> = appSettings.observeLanguage()
}