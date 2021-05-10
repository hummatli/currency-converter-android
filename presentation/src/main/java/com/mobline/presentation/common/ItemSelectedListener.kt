package com.mobline.presentation.common

import android.view.View
import android.widget.AdapterView

abstract class ItemSelectedListener: AdapterView.OnItemSelectedListener {
    override fun onNothingSelected(p0: AdapterView<*>?) {
        // Empty
    }

    abstract override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long)
}