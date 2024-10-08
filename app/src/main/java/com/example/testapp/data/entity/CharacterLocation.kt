package com.example.testapp.data.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CharacterLocation(
    @SerializedName("name")
val name: String?
) : Parcelable

