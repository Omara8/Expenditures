package com.planatech.expenditures

import android.app.Application
import android.content.Context
import com.planatech.expenditures.utils.PreferencesUtils

class App: Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: App? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }

        fun getApplication(): App {
            return instance ?: App()
        }
    }

    override fun onCreate() {
        super.onCreate()
        PreferencesUtils.initPreferences(this)
    }

}