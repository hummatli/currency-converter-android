package com.mobline.domain.repository

import com.mobline.domain.constant.AppLanguage
import kotlinx.coroutines.flow.Flow

interface AppSettingsDataSource {
    fun getAppLanguage(): AppLanguage
    fun setAppLanguage(langCode: AppLanguage)
    fun observeLanguage(): Flow<AppLanguage>
}