package com.planatech.expenditures.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.google.firebase.auth.FirebaseUser

object PreferencesUtils {

    private var sharedPreferences: SharedPreferences? = null

    fun initPreferences(context: Context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    }

    fun setUserData(user: FirebaseUser?) {
        sharedPreferences?.let {
            it.edit().putString(USER_ID, user?.uid).apply()
            it.edit().putString(USER_NAME, user?.displayName).apply()
            it.edit().putString(USER_IMAGE, user?.photoUrl?.toString()).apply()
            it.edit().putString(USER_EMAIL, user?.email).apply()
        }
    }

    fun getUserId(): String? {
        return sharedPreferences?.getString(USER_ID, null)
    }

    fun getUserName(): String? {
        return sharedPreferences?.getString(USER_NAME, null)
    }

    fun getUserImage(): String? {
        return sharedPreferences?.getString(USER_IMAGE, null)
    }

    fun getUserEmail(): String? {
        return sharedPreferences?.getString(USER_EMAIL, null)
    }

    fun logOut() {
        sharedPreferences?.let {
            it.edit().clear().commit()
        }
    }

}