package com.example.testapp.ui.planner

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.testapp.data.entity.AllPlanner
import com.example.testapp.data.entity.ChildPlannerDB
import com.example.testapp.data.entity.PlannerDB
import com.example.testapp.databinding.FragmentPlannerBinding
import com.example.testapp.ui.BaseFragment
import com.example.testapp.ui.login.LoginDialogFragment
import com.example.testapp.ui.login.ResetPassDialogFragment
import com.example.testapp.ui.planner.child_planner.AddChildTicketDialogFragment
import com.example.testapp.ui.planner.child_planner.ChildPlannerAdapter
import com.example.testapp.ui.register.RegisterDialogFragment
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class PlannerFragment : BaseFragment<FragmentPlannerBinding>(FragmentPlannerBinding::inflate) {

    private val plannerViewModel: PlannerViewModel by activityViewModels()
    private val plannerAdapter = PlannerAdapter()

    private var plannerList : MutableList<PlannerDB> = mutableListOf()
    private var childPlannerList : MutableList<ChildPlannerDB> = mutableListOf()
    private var allPlannerList : MutableList<AllPlanner> = mutableListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            addBtn.setOnClickListener {
                AddTicketDialogFragment.newInstance()
                    .show(childFragmentManager, AddTicketDialogFragment.TAG)
            }

            rvPlanner.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = plannerAdapter
                fetchPlanner()
            }

            plannerAdapter.listener = {
                if (plannerAdapter.checkListener) {
                    plannerViewModel.removeTicket(it.id)
                }
            }
            plannerAdapter.childAddListener = {
                AddChildTicketDialogFragment.newInstance(it)
                    .show(childFragmentManager, AddChildTicketDialogFragment.TAG)
            }
        }



        plannerViewModel.getTag().observe(viewLifecycleOwner) {

            when (it) {
                AddTicketDialogFragment.TAG -> {
                    (childFragmentManager.findFragmentByTag(AddTicketDialogFragment.TAG) as? AddTicketDialogFragment)?.dismiss()
                }
                AddChildTicketDialogFragment.TAG -> {
                    (childFragmentManager.findFragmentByTag(AddChildTicketDialogFragment.TAG) as? AddChildTicketDialogFragment)?.dismiss()
                }
                else -> {}
            }
        }
    }

//    override fun onResume() {
//        super.onResume()
//        plannerViewModel.getAllPlannerList().observe(viewLifecycleOwner) {
//            allPlannerList.clear()
//            allPlannerList.addAll(it)
//        }
//    }

    private fun fetchPlanner() {
        lifecycleScope.launch {
            plannerViewModel.apply {
                getPlanner().distinctUntilChanged().collectLatest {
                    plannerAdapter.items = it.sortedBy { planner ->
                        planner.date
                    }.toMutableList()
                    plannerList.clear()
                    plannerList.addAll(
                        it.sortedBy { planner ->
                            planner.date
                        }.toMutableList()
                    )
//                    val asyncWork = async { getAllPlanner(plannerList, childPlannerList) }
//                    plannerAdapter.items = getAllPlanner(plannerList, childPlannerList)
                }
            }
        }
        lifecycleScope.launch {
            plannerViewModel.apply {
                getChildPlanner().distinctUntilChanged().collectLatest {
                    childPlannerList.clear()
                    childPlannerList.addAll(
                        it.sortedBy { planner ->
                            planner.date
                        }.toMutableList()
                    )
//                    val asyncWork = async { getAllPlanner(plannerList, childPlannerList) }
//                    plannerAdapter.items = getAllPlanner(plannerList, childPlannerList)
                }
            }
        }
    }
//
//    private fun fetchChildPlanner() {
//        lifecycleScope.launch {
//            plannerViewModel.getChildPlanner().distinctUntilChanged().collectLatest {
//                plannerAdapter.childItems = it.toMutableList()
//            }
//        }
//    }

    private fun getAllPlanner(parentList: MutableList<PlannerDB>, childList: List<ChildPlannerDB>) : MutableList<AllPlanner> {
        val allPlannerList = mutableListOf<AllPlanner>()
        val individualChild = mutableListOf<ChildPlannerDB>()
        if (parentList.isNotEmpty()) {
            do {
//                individualChild.clear()
                for (child in childList) {
                    if (child.parentId == parentList.last().id)
                        individualChild.add(child)
                }
                allPlannerList.add(
                    AllPlanner(
                        id = parentList.last().id,
                        body = parentList.last().body!!,
                        date = parentList.last().date!!,
                        child = individualChild
                    )
                )
                parentList.removeLast()
            } while (parentList.isNotEmpty())
        }

        return allPlannerList
    }

}