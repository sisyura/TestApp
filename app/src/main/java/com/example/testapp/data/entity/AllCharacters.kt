package com.example.testapp.data.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AllCharacters(
    @SerializedName("info")
    val pageInfo: PageInfo?,
    @SerializedName("results")
    val itemCharacter: List<ItemCharacter?>?
) : Parcelable
