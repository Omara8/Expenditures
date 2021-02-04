package com.planatech.expenditures.utils

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.planatech.expenditures.model.Transaction
import com.planatech.expenditures.model.User
import com.planatech.expenditures.utils.extensions.checkAndUpdateLastSalaryDay
import com.planatech.expenditures.utils.extensions.encodeDots


class DatabaseUtils {

    private val TAG = DatabaseUtils::class.java.name
    private val dataBase = Firebase.database.reference
    private val userId = PreferencesUtils.getUserId()
    private val userName = PreferencesUtils.getUserName()
    private val userEmail = PreferencesUtils.getUserEmail()?.encodeDots()
    private val userImage = PreferencesUtils.getUserImage()?.encodeDots()

    companion object {
        private var instance: DatabaseUtils? = null

        fun initDatabase() {
            instance = DatabaseUtils()
        }

        fun getDatabase(): DatabaseUtils {
            return instance ?: DatabaseUtils()
        }
    }

    fun getPagedOptions(): Query {
        return dataBase.child(USERS).child(userId!!).child(TRANSACTIONS)
    }

    fun getTransactions(key: String) {
        dataBase.child(USERS).child(userId!!).child(TRANSACTIONS).orderByKey().endAt(key)
            .limitToLast(
                100
            ).addValueEventListener(
                object :
                    ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.children.forEach {
                            val data = it.getValue<Transaction>()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.d(TAG, "onCancelled: getTransaction")
                    }
                })
    }

    fun initUser(callBack: () -> Unit) {
        userId?.let {
            dataBase.child(USERS).child(it).child(USER_INFO).addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val data = snapshot.value
                    if (data == null) {
                        val user = User(
                            it, userName, userEmail, userImage, 0f, null,
                            0f, 0f, 0f, null
                        )
                        dataBase.child(USERS).child(it).child(USER_INFO).setValue(user)
                            .addOnCompleteListener {
                                Log.d(TAG, "initUser: onComplete")
                            }.addOnSuccessListener {
                                callBack()
                            }
                    } else
                        callBack()
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d(TAG, "onCancelled: cancelled")
                }

            })
        }
    }

    fun loadUserInfo(callBack: (user: User?) -> Unit) {
        userId?.let {
            dataBase.child(USERS).child(it).child(USER_INFO).addValueEventListener(
                object :
                    ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val user = snapshot.getValue<User>()
                        callBack(user)
                        user?.lastSalaryDate?.checkAndUpdateLastSalaryDay { numberOfMonthsToUpdate, lastSalaryDay ->
                            if (numberOfMonthsToUpdate > 0) {
                                updateBalance(user, numberOfMonthsToUpdate - 1, lastSalaryDay)
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.d(TAG, "onCancelled: getTransaction")
                    }
                })
        }
    }

    private fun updateBalance(user: User, numberOfMonthsToUpdate: Int, lastSalaryDay: String) {
        val userBalance: Float = user.balance ?: 0f
        val userSalaryAmount = user.salaryAmount ?: 0f
        val totalMonthlyIncome: Float = user.totalMonthlyIncome ?: 0f
        val totalMonthlyPayments: Float = user.totalMonthlyPayments ?: 0f

        val newBalance =
            userBalance + userSalaryAmount * numberOfMonthsToUpdate + totalMonthlyIncome * numberOfMonthsToUpdate - totalMonthlyPayments * numberOfMonthsToUpdate

        user.balance = newBalance
        user.lastSalaryDate = lastSalaryDay
        if (numberOfMonthsToUpdate > 0)
            updateUserInfo(user, null)
    }

    fun updateUserInfo(user: User, callBack: (() -> Unit)?) {
        userId?.let {
            dataBase.child(USERS).child(it).child(USER_INFO).setValue(user).addOnSuccessListener {
                callBack?.let {
                    it()
                }
            }
        }
    }

    fun addTransaction(transaction: Transaction, callBack: () -> Unit) {
        userId?.let {
            dataBase.child(USERS).child(it).child(TRANSACTIONS).push().setValue(transaction)
                .addOnSuccessListener {
                    callBack()
                }
        }
    }

}