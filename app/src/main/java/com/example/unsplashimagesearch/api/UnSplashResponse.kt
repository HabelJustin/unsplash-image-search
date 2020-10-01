package com.example.unsplashimagesearch.api

import com.example.unsplashimagesearch.data.UnSplashPhoto
import retrofit2.Response

data class UnSplashResponse(
    val results: List<UnSplashPhoto>
)