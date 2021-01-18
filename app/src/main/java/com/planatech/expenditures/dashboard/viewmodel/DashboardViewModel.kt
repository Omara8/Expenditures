package com.planatech.expenditures.dashboard.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.planatech.expenditures.model.User
import com.planatech.expenditures.utils.DatabaseUtils

class DashboardViewModel : ViewModel() {

    private val database = DatabaseUtils.getDatabase()

    private var _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    fun loadUserInfo() {
        database.loadUserInfo {
            _user.postValue(it)
        }
    }

}