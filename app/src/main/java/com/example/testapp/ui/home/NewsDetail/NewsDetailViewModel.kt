package com.example.testapp.ui.home.NewsDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.data.CharactersRepository
import com.example.testapp.data.entity.ItemCharacter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsDetailViewModel @Inject constructor(
    private val repository: CharactersRepository
) : ViewModel(){

    fun saveCharacter(character: ItemCharacter) {
        viewModelScope.launch {
            try {
                repository.createCharacter(character)
            } catch (e: Exception) {
                repository.removeCharacter(character.id)
            }
        }
    }

}