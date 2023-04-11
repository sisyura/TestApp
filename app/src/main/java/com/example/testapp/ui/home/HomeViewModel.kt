package com.example.testapp.ui.home

import android.app.appsearch.SearchResult
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.testapp.data.CharactersRepository
import com.example.testapp.data.RetrofitRepository
import com.example.testapp.data.entity.ItemCharacter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.ranges.CharRange.Companion.EMPTY

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: RetrofitRepository,
    private val dbRepository: CharactersRepository
): ViewModel() {

    var isSaved = false
    private var characterListFlow : Flow<PagingData<ItemCharacter>> = flow {
        getCharactersListFlow().collectLatest {
            it
        }
    }

    fun isSaved(characterId: Int) {
        viewModelScope.launch {
            isSaved = dbRepository.isRowIsExist(characterId)
        }
    }

    fun getCharactersListFlow() : Flow<PagingData<ItemCharacter>> {
        characterListFlow = repository.getSearchResultStream().cachedIn(viewModelScope)
        return characterListFlow
    }

    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }
}