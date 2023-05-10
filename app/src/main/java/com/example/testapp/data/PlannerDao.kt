package com.example.testapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.testapp.data.entity.PlannerDB
import kotlinx.coroutines.flow.Flow

@Dao
interface PlannerDao {
    @Query("SELECT * FROM planner")
    fun getAllPlanner(): Flow<List<PlannerDB>>

    @Query("SELECT * FROM planner WHERE id = :id")
    fun getPlanner(id: Int): Flow<PlannerDB>

    @Query("DELETE FROM planner WHERE id = :id")
    suspend fun deletePlanner(id: Int)

    @Insert
    suspend fun insertPlanner(planner: PlannerDB) : Long
}