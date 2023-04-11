package com.example.testapp.ui.dashboard

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
}