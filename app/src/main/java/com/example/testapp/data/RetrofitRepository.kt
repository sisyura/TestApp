package com.example.testapp.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.testapp.api.RetrofitService
import com.example.testapp.data.entity.ItemCharacter
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RetrofitRepository @Inject constructor( private val service: RetrofitService) {

    fun getSearchResultStream (): Flow<PagingData<ItemCharacter>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 20),
            pagingSourceFactory = {RetrofitPagingSource(service) }
        ).flow
    }
}