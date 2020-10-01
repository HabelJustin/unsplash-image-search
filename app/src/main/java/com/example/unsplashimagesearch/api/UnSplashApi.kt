package com.example.unsplashimagesearch.api


import com.example.unsplashimagesearch.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface UnSplashApi {

    companion object {
        const val BASE_URL = "https://api.unsplash.com/"
        const val UNSPLASH_ACCESS_KEY = BuildConfig.UNSPLASH_ACCESS_KEY
    }

    @Headers(
        "Accept-Version: v1",
        "Authorization: Client-ID $UNSPLASH_ACCESS_KEY"
    )
    @GET("search/photos")
    suspend fun searchPhoto(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Response<UnSplashResponse>

}