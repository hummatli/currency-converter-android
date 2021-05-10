package com.mobline.presentation.extensions

import android.content.res.Resources
import android.util.TypedValue


fun Number.toPx(): Float {
    val r: Resources = Resources.getSystem()
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), r.displayMetrics)
}