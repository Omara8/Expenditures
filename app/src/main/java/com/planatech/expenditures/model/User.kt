package com.planatech.expenditures.model

import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class User(
    val id: String = "",
    val name: String? = "",
    val email: String? = "",
    val image: String? = "",
    var balance: Float? = 0f,
    val salaryDay: String? = "",
    val salaryAmount: Float? = 0f,
    val totalMonthlyPayments: Float? = 0f,
    val totalMonthlyIncome: Float? = 0f,
    var lastSalaryDate: String? = ""
) : Serializable