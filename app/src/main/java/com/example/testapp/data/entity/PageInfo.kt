package com.example.testapp.data.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PageInfo(
    @SerializedName("pages")
    val totalPages: Int?
) : Parcelable
