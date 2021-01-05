package com.planatech.expenditures.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

object PreferencesUtils {

    private var sharedPreferences: SharedPreferences? = null

    fun initPreferences(context: Context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    }

    fun setUserId(id: String){
        sharedPreferences?.let {
            it.edit().putString(USER_ID, id).apply()
        }
    }

    fun getUserId(): String?{
        return sharedPreferences?.getString(USER_ID, null)
    }

    fun logOut() {
        sharedPreferences?.let {
            it.edit().clear().commit()
        }
    }

}