package com.example.iteminventoryapp;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.util.Objects;

public class Validation {
    private final Context context;

    public Validation(Context context) {
        this.context = context;
    }

    public boolean isInputTextFilled(TextInputEditText textInputEditText, TextInputLayout textInputLayout, String message) {
        String value = Objects.requireNonNull(textInputEditText.getText()).toString().trim();
        if (value.isEmpty()) {
            textInputLayout.setError(message);
            removeKeyboard(textInputEditText);
            return false;
        } else {
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }

    public boolean isInputTextNull(TextInputEditText textInputEditText, TextInputLayout textInputLayout, String message) {
        String value = Objects.requireNonNull(textInputEditText.getText()).toString().trim();
        if (value.isEmpty()) {
            textInputLayout.setError(message);
            removeKeyboard(textInputEditText);
            return false;
        } else {
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }

    public boolean isInputEditTextMatches(TextInputEditText textInputEditText1, TextInputEditText textInputEditText2, TextInputLayout textInputLayout, String message) {
        String value1 = Objects.requireNonNull(textInputEditText1.getText()).toString().trim();
        String value2 = Objects.requireNonNull(textInputEditText2.getText()).toString().trim();
        if (!value1.contentEquals(value2)) {
            textInputLayout.setError(message);
            removeKeyboard(textInputEditText2);
            return false;
        } else {
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }

    private void removeKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}