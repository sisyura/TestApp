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
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlannerViewModel @Inject constructor(
    private val repository: PlannerRepository,
    private val childRepository: ChildPlannerRepository
) : ViewModel() {

    private var myDialogTag = MutableLiveData<String>()

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
            childRepository.removePlannerByParenId(id)
        }
    }

    fun getChildPlanner() : Flow<List<ChildPlannerDB>> =
        childRepository.getAllPlanner()

    fun addChildTicket(date: Long, body: String, parentId: Int) {
        viewModelScope.launch {
            childRepository.createPlanner(ChildPlannerDB((0..9999).random(), parentId, body, date))
        }
    }

    fun removeChildTicket(childId: Int) {
        viewModelScope.launch {
            delay(500)
            childRepository.removePlanner(childId)
        }
    }
}