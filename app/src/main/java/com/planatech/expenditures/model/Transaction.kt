package com.planatech.expenditures.model

import androidx.annotation.Keep
import java.io.Serializable
import java.util.*

@Keep
data class Transaction(
    val id: String = "",
    val name: String = "",
    val date: Date = Date(),
    val amount: Float = 0f,
    //only valid for ongoing transactions
    val endDate: Date = Date(),
    val type: TransactionType = TransactionType.ONGOING_INCOME
) : Serializable
