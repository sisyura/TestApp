package com.example.testapp.data

import com.example.testapp.data.entity.ChildPlannerDB
import javax.inject.Inject

class ChildPlannerRepository @Inject constructor(private val childPlannerDao: ChildPlannerDao){

    fun getAllPlanner() = childPlannerDao.getAllPlanner()

    fun getPlanner(id: Int) = childPlannerDao.getPlanner(id)

    suspend fun createPlanner(childPlanner: ChildPlannerDB) = childPlannerDao.insertPlanner(childPlanner)

    suspend fun removePlanner(id: Int) = childPlannerDao.deletePlanner(id)

    companion object {
        @Volatile private var instance: ChildPlannerRepository? = null

        fun getInstance(childPlannerDao: ChildPlannerDao) =
            instance ?: synchronized(this) {
                instance ?: ChildPlannerRepository(childPlannerDao).also { instance = it }
            }
    }
}