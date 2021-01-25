package com.planatech.expenditures

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.planatech.expenditures.utils.AuthenticationUtils

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        for (i in 1..100){
//            DatabaseUtils.getDatabase().addTransaction(
//                Transaction(
//                    i.toString(),
//                    "transaction",
//                    Date().formatForFireBase(),
//                    i.toFloat(),
//                    Date().formatForFireBase(),
//                    TransactionType.ONE_TIME_PAYMENT
//                ),{}
//            )
//        }
    }

    fun signOut() {
        AuthenticationUtils.signOut {
            startActivity(Intent(this, SplashActivity::class.java))
            finish()
        }
    }

}