package com.example.testapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Calendar

@Entity(tableName = "characters")
data class ItemCharacterDB(
    @PrimaryKey val id : Int,
    val name : String?,
    val status : String?,
    val species : String?,
    val gender : String?,
    val origin : String?,
    val location : String?,
    val image : String?,
    val addDate : Long?,
)
