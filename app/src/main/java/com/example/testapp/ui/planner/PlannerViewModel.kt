package com.example.testapp.ui.planner

import androidx.lifecycle.*
import com.example.testapp.data.ChildPlannerRepository
import com.example.testapp.data.PlannerRepository
import com.example.testapp.data.entity.AllPlanner
import com.example.testapp.data.entity.ChildPlannerDB
import com.example.testapp.data.entity.ItemCharacter
import com.example.testapp.data.entity.PlannerDB
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlannerViewModel @Inject constructor(
    private val repository: PlannerRepository,
    private val childRepository: ChildPlannerRepository
) : ViewModel() {

    private var myDialogTag = MutableLiveData<String>()
    private var allPlannerListLiveData = MutableLiveData<MutableList<AllPlanner>>()

    private var plannerList: MutableList<PlannerDB> = mutableListOf()
    private var childPlannerList: MutableList<ChildPlannerDB> = mutableListOf()
    private var childIndividualPlannerList: MutableList<ChildPlannerDB> = mutableListOf()
    private var allPlannerList: MutableList<AllPlanner> = mutableListOf()

    init {
        getPlanner()
        getChildPlanner()
    }

    fun sendTag(tag: String) {
        myDialogTag.value = tag
    }

    fun getTag(): LiveData<String> {
        return myDialogTag
    }

    fun getPlanner() : Flow<List<PlannerDB>> =
        repository.getAllPlanner()

    fun addTicket(date: Long, body: String) {
        viewModelScope.launch {
            repository.createPlanner(PlannerDB((0..9999).random(), body, date))
        }
    }

    fun removeTicket(id: Int) {
        viewModelScope.launch {
            delay(500)
            repository.removePlanner(id)
        }
    }

    fun getChildPlanner() : Flow<List<ChildPlannerDB>> =
        childRepository.getAllPlanner()

    fun addChildTicket(date: Long, body: String, parentId: Int) {
        viewModelScope.launch {
            childRepository.createPlanner(ChildPlannerDB((0..9999).random(), parentId, body, date))
        }
    }

    fun removeChildTicket(id: Int) {
        viewModelScope.launch {
            delay(500)
            childRepository.removePlanner(id)
        }
    }

//    suspend fun setAllPlannerList() : MutableList<AllPlanner> {
//        allPlannerList.clear()
//        for (planner in plannerList) {
//            childIndividualPlannerList.clear()
//            for (childPlanner in childPlannerList) {
//                if (childPlanner.parentId == planner.id) {
//                    childIndividualPlannerList.add(childPlanner)
//                }
//            }
//            allPlannerList.add(AllPlanner(id = planner.id, body = planner.body!!, date = planner.date!!, child = childIndividualPlannerList))
//            delay(200L)
//        }
//        childIndividualPlannerList.clear()
//        return allPlannerList
////        allPlannerListLiveData.value = allPlannerList
//    }
//
//    fun getAllPlannerList(): LiveData<MutableList<AllPlanner>> {
//        return allPlannerListLiveData
//    }

}