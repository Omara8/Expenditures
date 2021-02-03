package com.planatech.expenditures.utils.extensions

import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

fun TextInputEditText.isNotEmpty(error: Int, parent: TextInputLayout?): Boolean {
    return if (this.text.toString().isEmpty()) {
        parent?.error = context.getString(error)
        this.requestFocus()
        false
    } else {
        parent?.error = null
        true
    }
}

fun TextInputEditText.isMoreThanZero(error: Int, parent: TextInputLayout?): Boolean {
    return if (this.text.toString().isEmpty() || this.text.toString().toDouble() == 0.0) {
        parent?.error = context.getString(error)
        false
    } else {
        parent?.error = null
        true
    }
}
