package com.planatech.expenditures.model

import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class User(
    val id: String,
    val name: String?,
    val email: String?,
    val image: String?,
    val balance: Float?,
    val salaryDay: String?,
    val salaryAmount: Float?,
    val totalMonthlyPayments: Float?,
    val totalMonthlyIncome: Float?,
    val transactions: List<Transaction>?
) : Serializable