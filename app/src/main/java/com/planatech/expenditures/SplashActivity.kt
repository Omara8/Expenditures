package com.planatech.expenditures

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.planatech.expenditures.utils.*

class SplashActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        AnalyticsUtils.logEvent(AnalyticsEvents.AppOpened)
        setLoginListener()
        checkExistingUser()
    }

    private fun checkExistingUser() {
        Handler().postDelayed({
            val userId = PreferencesUtils.getUserId()
            if (userId != null) {
                //go to app main screen
//                splash_main_content?.showContent(listOf(R.id.login_button))
                openMainActivity()
            } else
                //show login screen
                showToast(this, "Login to continue")
//                splash_main_content?.showContent()
        }, SPLASH_DISPLAY_TIME)
    }

    private fun setLoginListener() {

    }

    private fun openMainActivity() {
        AnalyticsUtils.logEvent(AnalyticsEvents.SignedIn)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}