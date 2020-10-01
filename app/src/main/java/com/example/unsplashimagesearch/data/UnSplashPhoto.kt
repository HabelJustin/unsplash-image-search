package com.example.unsplashimagesearch.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UnSplashPhoto(
    val id: String,
    val description: String?,
    val urls: UnSplashPhotoUrls,
    val user: UnSplashUser
): Parcelable