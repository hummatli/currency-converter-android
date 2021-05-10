package com.mobline.presentation.common;

import android.text.Editable;

public abstract class EditTextTextWatcher implements android.text.TextWatcher {

    private boolean editing;

    @Override
    public final void beforeTextChanged(CharSequence s, int start,
                                        int count, int after) {
        // Empty
    }


    @Override
    public final void onTextChanged(CharSequence s, int start,
                                    int before, int count) {
        // No usage
    }

    @Override
    public final void afterTextChanged(Editable s) {
        if (editing)
            return;

        editing = true;
        try {
            afterTextChange(s);
        } finally {
            editing = false;
        }
    }

    public boolean isEditing() {
        return editing;
    }

    protected abstract void afterTextChange(Editable s);
}
