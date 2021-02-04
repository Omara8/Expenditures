package com.planatech.expenditures.utils.extensions

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.ChronoUnit
import java.util.*

fun String.encodeDots(): String {
    return this.replace(".", "%2E")
}

fun String.decodeDots(): String {
    return this.replace("%2E", ".")
}

fun String.checkAndUpdateLastSalaryDay(callback: (Int, String) -> Unit) {
    val inputFormatter: DateFormat = SimpleDateFormat("dd MMMM yyyy")
    val lastSalaryDate = inputFormatter.parse(this) as Date
    val lastSalary = lastSalaryDate.formatForCalculations()
    val today = Date().formatForCalculations()
    val monthsBetween = ChronoUnit.MONTHS.between(
        YearMonth.from(LocalDate.parse(lastSalary)),
        YearMonth.from(LocalDate.parse(today))
    )
    val calendar = Calendar.getInstance()
    calendar.time = lastSalaryDate
    calendar.add(Calendar.MONTH, (monthsBetween - 1).toInt())

    val newLastSalaryDate: Date = calendar.time

    callback(monthsBetween.toInt(), newLastSalaryDate.formatForFireBase())
}