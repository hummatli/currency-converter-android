package com.mobline.presentation.extensions

import android.text.InputType
import android.text.method.DigitsKeyListener
import android.widget.EditText
import com.redmadrobot.inputmask.MaskedTextChangedListener
import com.redmadrobot.inputmask.helper.Mask
import com.redmadrobot.inputmask.model.Notation
import timber.log.Timber

fun EditText.addNumberMasking(
    pattern: String,
    onChange: (String) -> Unit
) {
    Mask.getOrCreate(pattern, listOf(Notation('.', ".,", true)))
    this.inputType = InputType.TYPE_CLASS_NUMBER
    this.keyListener = DigitsKeyListener.getInstance("0123456789 -.,/")

    val listener = MaskedTextChangedListener(
        pattern,
        this,
        object : MaskedTextChangedListener.ValueListener {
            override fun onTextChanged(
                maskFilled: Boolean,
                extractedValue: String,
                formattedValue: String
            ) {
                onChange(extractedValue)
            }
        })

    listener.autoskip = true

    this.addTextChangedListener(listener)
    this.onFocusChangeListener = listener
}

fun EditText.setTextIfDifferent(newText: String?) {
    Timber.i("Edit Text Difference")
    if (text.toString() != newText) {
        Timber.i("Edit Text Difference. setted ${newText}")
        setText(newText)
    }
}
