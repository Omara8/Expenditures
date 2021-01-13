package com.planatech.expenditures.model

import androidx.annotation.Keep
import java.io.Serializable

@Keep
enum class TransactionType(val value: String): Serializable {
    MONTHLY_INCOME("Monthly Income"),
    ONE_TIME_INCOME("One Time Income"),
    MONTHLY_PAYMENT("Monthly Payment"),
    ONE_TIME_PAYMENT("One Time Payment")
}