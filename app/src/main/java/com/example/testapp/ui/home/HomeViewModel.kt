package com.example.testapp.ui.home

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.testapp.data.CharactersRepository
import com.example.testapp.data.RetrofitRepository
import com.example.testapp.data.entity.ItemCharacter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: RetrofitRepository,
    private val dbRepository: CharactersRepository
): ViewModel() {

    var isSaved = false

    fun isSaved(characterId: Int) {
        viewModelScope.launch {
            isSaved = dbRepository.isRowIsExist(characterId)
        }
    }

    fun getCharactersListFlow() : Flow<PagingData<ItemCharacter>> {
        return repository.getSearchResultStream().cachedIn(viewModelScope)
    }
}