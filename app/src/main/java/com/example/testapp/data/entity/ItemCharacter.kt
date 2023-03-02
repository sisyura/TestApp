package com.example.testapp.data.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemCharacter(
    @SerializedName("id")
    val id : Int,
    @SerializedName("name")
    val name : String?,
    @SerializedName("status")
    val status : String?,
    @SerializedName("species")
    val species : String?,
    @SerializedName("gender")
    val gender : String?,
    @SerializedName("origin")
    val origin : CharacterOrigin?,
    @SerializedName("location")
    val location : CharacterLocation?,
    @SerializedName("image")
    val image : String?,
) : Parcelable
