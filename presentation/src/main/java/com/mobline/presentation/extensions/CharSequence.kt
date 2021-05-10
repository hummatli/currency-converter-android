package com.mobline.presentation.extensions

import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan

fun CharSequence.withColor(color: Int): Spanned {
    val spanned = SpannableString(this)
    spanned.setSpan(ForegroundColorSpan(color), 0, this.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
    return spanned
}

fun CharSequence.withBold(): Spanned {
    val spanned = SpannableString(this)
    spanned.setSpan(StyleSpan(Typeface.BOLD), 0, this.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
    return spanned
}