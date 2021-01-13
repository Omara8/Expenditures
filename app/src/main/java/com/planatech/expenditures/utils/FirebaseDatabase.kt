package com.planatech.expenditures.utils

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.planatech.expenditures.model.User
import com.planatech.expenditures.utils.extensions.encodeDots

object FirebaseDatabase {

    private val TAG = FirebaseDatabase.javaClass.name
    private val dataBase = Firebase.database.reference
    private val userId = PreferencesUtils.getUserId()
    private val userName = PreferencesUtils.getUserName()
    private val userEmail = PreferencesUtils.getUserEmail()?.encodeDots()
    private val userImage = PreferencesUtils.getUserImage()?.encodeDots()

    fun initUser(callBack: () -> Unit) {
        userId?.let {
            dataBase.child(USERS).child(it).addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val data = snapshot.value
                    if (data == null) {
                        val user = User(
                            it, userName, userEmail, userImage, 0f, null,
                            0f, 0f, 0f, null
                        )
                        dataBase.child(USERS).child(it).setValue(user).addOnCompleteListener {
                            Log.d(TAG, "initUser: TEST")
                        }.addOnSuccessListener {
                            callBack()
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d(TAG, "onCancelled: cancelled")
                }

            })
        }
    }

}