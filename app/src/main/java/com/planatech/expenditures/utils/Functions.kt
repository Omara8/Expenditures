package com.planatech.expenditures.utils

import android.content.Context
import android.widget.Toast
import java.util.*

fun showToast(context: Context, msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}

fun generateUUID(): String {
    return UUID.randomUUID().toString()
}