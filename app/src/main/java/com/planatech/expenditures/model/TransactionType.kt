package com.planatech.expenditures.model

import androidx.annotation.Keep
import java.io.Serializable

@Keep
enum class TransactionType(val value: String) : Serializable {
    ONGOING_INCOME("Ongoing Income"),
    ONE_TIME_INCOME("One Time Income"),
    ONGOING_PAYMENT("Ongoing Payment"),
    ONE_TIME_PAYMENT("One Time Payment")
}