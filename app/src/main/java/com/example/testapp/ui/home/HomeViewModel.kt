package com.example.testapp.ui.home

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.example.testapp.data.NewsItem
import com.example.testapp.data.RetrofitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    repository: RetrofitRepository
): ViewModel() {

    val charactersItems = repository.getSearchResultStream().cachedIn(viewModelScope)

}