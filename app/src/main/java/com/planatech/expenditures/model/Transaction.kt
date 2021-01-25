package com.planatech.expenditures.model

import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class Transaction(
    val id: String = "",
    val name: String = "",
    val date: String = "",
    val amount: Float = 0f,
    //only valid for ongoing transactions
    val endDate: String = "",
    val type: TransactionType = TransactionType.ONGOING_INCOME
) : Serializable
