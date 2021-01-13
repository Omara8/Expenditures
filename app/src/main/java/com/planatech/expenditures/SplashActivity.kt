package com.planatech.expenditures

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.planatech.expenditures.utils.*

class SplashActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        AnalyticsUtils.logEvent(AnalyticsEvents.AppOpened)
        checkExistingUser()
    }

    private fun checkExistingUser() {
        Handler().postDelayed({
            val userId = PreferencesUtils.getUserId()
            if (userId != null) {
                //go to app main screen
                initUserAndOpenApp()
            } else
                //show login screen
                createSignInIntent()
        }, SPLASH_DISPLAY_TIME)
    }

    private fun initUserAndOpenApp() {
        DatabaseUtils.initUser {
            openMainActivity()
        }
    }

    private fun createSignInIntent() {
        val providers = arrayListOf(AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build())

        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setTheme(R.style.loginTheme)
                .setLogo(R.drawable.ic_launcher_foreground)
                .build(),
            99)
    }

    private fun openMainActivity() {
        AnalyticsUtils.logEvent(AnalyticsEvents.SignedIn)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 99) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                PreferencesUtils.setUserData(user)
                initUserAndOpenApp()
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                 showToast(this, response?.error.toString())
            }
        }
    }

}