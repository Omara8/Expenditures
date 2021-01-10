package com.planatech.expenditures

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.planatech.expenditures.utils.AuthenticationHelper

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
    }

    fun signOut(){
        AuthenticationHelper.signOut {
            startActivity(Intent(this, SplashActivity::class.java))
            finish()
        }
    }

}