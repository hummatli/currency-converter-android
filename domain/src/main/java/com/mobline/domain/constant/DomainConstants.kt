package com.mobline.domain.constant

import java.util.*

enum class AppLanguage {
    DE, EN;

    companion object {
        fun of(string: String): AppLanguage = values().firstOrNull { it.name == string } ?: EN
    }
}

fun String.toAppLanguage() = AppLanguage.of(toUpperCase(Locale.getDefault()))