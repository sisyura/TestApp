package com.example.testapp.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NotificationsViewModel : ViewModel() {
    private var myDialogTag = MutableLiveData<String>()

    fun sendTag(tag : String) {
        myDialogTag.value = tag
    }

    fun getTag() : LiveData<String> {
        return myDialogTag
    }

}