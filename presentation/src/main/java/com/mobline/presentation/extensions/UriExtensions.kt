package com.mobline.presentation.extensions

import android.content.Context
import android.net.Uri

fun Uri.getExtension(context: Context): String {
    return context.contentResolver.getType(this)?.split("/")?.last() ?: "png"
}