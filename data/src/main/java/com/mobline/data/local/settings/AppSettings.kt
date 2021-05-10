package com.mobline.data.local.settings

import com.mobline.domain.constant.AppLanguage
import kotlinx.coroutines.flow.Flow

interface AppSettings {
    fun getAppLanguage(): AppLanguage
    fun setAppLanguage(langCode: AppLanguage)
    fun observeLanguage(): Flow<AppLanguage>
}