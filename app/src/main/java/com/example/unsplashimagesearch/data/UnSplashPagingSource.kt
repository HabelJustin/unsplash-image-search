package com.example.unsplashimagesearch.data

import androidx.paging.PagingSource
import com.example.unsplashimagesearch.api.UnSplashApi
import retrofit2.HttpException
import java.io.IOException
import java.lang.Error

private const val UNSPLASH_STARTING_PAGE_INDEX = 1

class UnSplashPagingSource(
    private val unsplashApi: UnSplashApi,
    private val query: String
): PagingSource<Int, UnSplashPhoto>() {

    // initiate request source
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnSplashPhoto> {
        val position = params.key ?: UNSPLASH_STARTING_PAGE_INDEX

        return try {
            val response = unsplashApi.searchPhoto(query, position, params.loadSize)
            val photos = response.body()!!.results
            LoadResult.Page(
                data = photos,
                prevKey = if (position == UNSPLASH_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (photos.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }

    }
}