package com.planatech.expenditures

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.planatech.expenditures.utils.AuthenticationUtils

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun signOut(){
        AuthenticationUtils.signOut {
            startActivity(Intent(this, SplashActivity::class.java))
            finish()
        }
    }

}