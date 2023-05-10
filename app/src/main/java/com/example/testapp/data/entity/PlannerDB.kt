package com.example.testapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "planner")
data class PlannerDB(
    @PrimaryKey val id : Int,
    val body : String?,
    val date : Long?
)

