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

        //        val config: PagedList.Config = PagedList.Config.Builder()
//            .setEnablePlaceholders(false)
//            .setPrefetchDistance(10)
//            .setPageSize(20)
//            .build()
//        val options = DatabasePagingOptions.Builder<Transaction>()
//        return FirebaseRecyclerOptions.Builder<Transaction>()
//            .setQuery(query, Transaction::class.java)
//            .build()//.setQuery(query, config, Transaction::class.java).build()
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
                            0f, 0f, 0f
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
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.d(TAG, "onCancelled: getTransaction")
                    }
                })
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