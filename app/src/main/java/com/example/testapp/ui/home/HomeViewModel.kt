package com.example.testapp.ui.home

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.example.testapp.data.CharactersRepository
import com.example.testapp.data.NewsItem
import com.example.testapp.data.RetrofitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    repository: RetrofitRepository,
    private val dbRepository: CharactersRepository
): ViewModel() {

    val charactersItems = repository.getSearchResultStream().cachedIn(viewModelScope)

    var isSaved = false

    fun isSaved(characterId: Int) {
        viewModelScope.launch {
            isSaved = dbRepository.isRowIsExist(characterId)
        }
    }

}