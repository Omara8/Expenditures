package com.planatech.expenditures.model

import androidx.annotation.Keep
import java.io.Serializable
import java.util.*

@Keep
data class Transaction(
    val id: String,
    val date: Date,
    val amount: Float,
    val endDate: Date,
    val type: TransactionType
) : Serializable
