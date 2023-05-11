package com.example.testapp.data.entity

data class AllPlanner(
    val id: Int,
    val body: String?,
    val date: Long?,
    val child: List<ChildPlannerDB>
)
