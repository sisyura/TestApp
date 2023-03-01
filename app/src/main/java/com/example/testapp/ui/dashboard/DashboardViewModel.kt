package com.example.testapp.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DashboardViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text

    fun isValidEmail(currency: String?) =
        currency?.let {
            Regex("[1-9][0-9]{0,9}+[.,][0-9]{2}").matchEntire(it)
        }
}