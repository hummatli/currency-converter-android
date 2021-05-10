package com.mobline.presentation.tools

import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.TypefaceSpan
import java.text.SimpleDateFormat
import java.util.*


object Utils {
    fun updateBoldSpanWithBoldFont(text: CharSequence, color: Int): SpannableString {
        val new = SpannableString(text)
        val spans = new.getSpans(0, new.length, StyleSpan::class.java)
        for (boldSpan in spans) {
            val start: Int = new.getSpanStart(boldSpan)
            val end: Int = new.getSpanEnd(boldSpan)
            new.setSpan(TypefaceSpan("gilroy_bold"), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            new.setSpan(ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        return new
    }

    fun millisToDate(millis: Long): String {
        val dateFormat = "dd-MM-yyyy hh:mm"
        val simpleDateFormat = SimpleDateFormat(dateFormat, Locale.getDefault())

        val calendar = Calendar.getInstance()
        calendar.setTimeInMillis(millis)
        return simpleDateFormat.format(calendar.getTime())
    }
}