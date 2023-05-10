package com.example.testapp.data.entity

import androidx.room.PrimaryKey

data class AllPlanner(
    val id : Int,
    val body : String,
    val date : Long,
    val child : List<ChildPlannerDB>?
)
