package com.example.testapp.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testapp.data.CharactersRepository
import com.example.testapp.data.entity.ItemCharacterDB
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject internal constructor(
    repository: CharactersRepository
): ViewModel() {

    val allCharacter: Flow<List<ItemCharacterDB>> =
        repository.getCharacters()

//    private val _text = MutableLiveData<String>().apply {
//        value = "This is dashboard Fragment"
//    }
//    val text: LiveData<String> = _text
//
//    fun isValidEmail(currency: String?) =
//        currency?.let {
//            Regex("[1-9][0-9]{0,9}+[.,][0-9]{2}").matchEntire(it)
//        }
}