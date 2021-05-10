package com.mobline.presentation.extensions

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible

fun ViewGroup.inflate(
    layoutId: Int,
    container: ViewGroup? = null,
    attachToRoot: Boolean = false
): View {
    return LayoutInflater.from(context).inflate(layoutId, container, attachToRoot)
}

var TextView.textColor
    set(value) {
        setTextColor(ContextCompat.getColor(context, value))
    }
    get() = textColors.defaultColor

var View.backgroundTint: Int
    set(value) {
        backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, value))
    }
    get() = backgroundTintList?.defaultColor ?: 0

var ImageView.iconTint
    set(value) {
        imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, value))
    }
    get() = imageTintList?.defaultColor ?: 0

fun EditText.clear() {
    setText("")
}

//For view's visibility
fun View.makeVisible() {
    visibility = View.VISIBLE
}

fun View.makeInvisible() {
    visibility = View.INVISIBLE
}

fun View.makeGone() {
    visibility = View.GONE
}