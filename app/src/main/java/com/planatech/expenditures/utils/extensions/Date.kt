package com.planatech.expenditures.utils.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Date.formatForFireBase(): String {
    val sdf = SimpleDateFormat("dd MMMM yyyy", Locale.US)
    return sdf.format(this).toString()
}

fun Date.formatForCalculations(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    return sdf.format(this).toString()
}