package com.planatech.expenditures.utils.extensions

import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputLayout

fun MaterialAutoCompleteTextView.isNotEmpty(error: Int, parent: TextInputLayout?): Boolean {
    return if (this.text.toString().isEmpty()) {
        parent?.error = context.getString(error)
        this.requestFocus()
        false
    } else {
        parent?.error = null
        true
    }
}