package com.example.testapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.testapp.data.entity.ChildPlannerDB
import kotlinx.coroutines.flow.Flow

@Dao
interface ChildPlannerDao {
    @Query("SELECT * FROM planner_child")
    fun getAllPlanner(): Flow<List<ChildPlannerDB>>

    @Query("SELECT * FROM planner_child WHERE id = :id")
    fun getPlanner(id: Int): Flow<ChildPlannerDB>

    @Query("DELETE FROM planner_child WHERE id = :id")
    suspend fun deletePlanner(id: Int)

    @Query("DELETE FROM planner_child WHERE parentId = :parentId")
    suspend fun deletePlannerByParentId(parentId: Int)

    @Insert
    suspend fun insertPlanner(childPlanner: ChildPlannerDB) : Long
}