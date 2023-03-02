package com.example.testapp.data.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PageInfo(
    @SerializedName("next")
    val nextPage: String?,
    @SerializedName("prev")
    val prevPage: String?
) : Parcelable
