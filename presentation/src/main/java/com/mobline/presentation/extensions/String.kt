package com.mobline.presentation.extensions

import timber.log.Timber
import java.math.BigDecimal

fun BigDecimal.toAmountString(currency: String, withSign: Boolean = false): String {
    val formatText = if (withSign) {
        "%+.2f"
    } else {
        "%.2f"
    }
    return if (currency.isBlank()) {
        formatText.format(this)
    } else {
        "%s %s".format(formatText.format(this), currency)
    }
}

fun String.containsPhoneCharactersOnly(): Boolean {
    return this.matches(Regex("\\+?\\d+(\\.\\d+)?"))
}

fun String.withPhonePrefix(): String {
    return "+994" + this.takeLast(9)
}

fun String.toSafeBigDecimal(): BigDecimal {
    return try {
        this.toBigDecimalOrNull() ?: BigDecimal.ZERO
    } catch (e: Exception) {
        Timber.e(e)
        BigDecimal.ZERO
    }
}