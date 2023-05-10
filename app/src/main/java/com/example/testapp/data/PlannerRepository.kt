package com.example.testapp.data

import com.example.testapp.data.entity.PlannerDB
import javax.inject.Inject

class PlannerRepository @Inject constructor(private val plannerDao: PlannerDao){

    fun getAllPlanner() = plannerDao.getAllPlanner()

    fun getPlanner(id: Int) = plannerDao.getPlanner(id)

    suspend fun createPlanner(planner: PlannerDB) = plannerDao.insertPlanner(planner)

    suspend fun removePlanner(id: Int) = plannerDao.deletePlanner(id)

    companion object {
        @Volatile private var instance: PlannerRepository? = null

        fun getInstance(plannerDao: PlannerDao) =
            instance ?: synchronized(this) {
                instance ?: PlannerRepository(plannerDao).also { instance = it }
            }
    }
}