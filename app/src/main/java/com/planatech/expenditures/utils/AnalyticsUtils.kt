package com.planatech.expenditures.utils

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import com.planatech.expenditures.App

enum class AnalyticsEvents(val value: String) {
    AppOpened("app_opened"),
    SignedIn("signed_in"),
    SignedOut("signed_out")
}

object AnalyticsUtils {
    private var firebaseAnalytics: FirebaseAnalytics =
        FirebaseAnalytics.getInstance(App.applicationContext())

    fun logEvent(event: AnalyticsEvents, itemName: String? = null) {
        firebaseAnalytics.logEvent(event.value) {
            param(FirebaseAnalytics.Param.CONTENT_TYPE, "event")
            itemName?.let {
                param("itemName", it)
            }
        }
    }
}