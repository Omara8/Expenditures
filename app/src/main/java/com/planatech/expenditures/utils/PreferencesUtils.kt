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

    fun setUserData(user: FirebaseUser?){
        sharedPreferences?.let {
            it.edit().putString(USER_ID, user?.uid).apply()
            it.edit().putString(USER_IMAGE, user?.photoUrl?.toString()).apply()
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