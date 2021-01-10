package com.planatech.expenditures.utils

import android.content.Context
import com.firebase.ui.auth.AuthUI

object AuthenticationHelper {

    var context: Context? = null

    fun initAuthHelper(context: Context) {
        this.context = context
    }

    fun signOut(callBack: () -> Unit) {
        context?.let {
            AuthUI.getInstance()
                .signOut(it)
                .addOnCompleteListener { _ ->
                    showToast(it, "Successfully Logged Out")
                    PreferencesUtils.logOut()
                    callBack()
                }
        }
    }

}