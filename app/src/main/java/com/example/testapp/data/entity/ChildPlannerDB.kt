package com.example.testapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "planner_child")
data class ChildPlannerDB(
    @PrimaryKey val id : Int,
    val parentId : Int?,
    val body : String?,
    val date : Long?
)
