package com.example.testapp.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.testapp.api.RetrofitService
import com.example.testapp.data.entity.ItemCharacter

private const val STARTING_PAGE_INDEX = 1

class RetrofitPagingSource(
    private val service: RetrofitService
) : PagingSource<Int, ItemCharacter>() {

    override fun getRefreshKey(state: PagingState<Int, ItemCharacter>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ItemCharacter> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = service.getCharacters(page)
            val character = response.characters
            LoadResult.Page(
                data = character,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (page == response.pageInfo?.totalPages) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}