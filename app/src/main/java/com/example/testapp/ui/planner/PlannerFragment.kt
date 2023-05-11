package com.example.testapp.ui.planner

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.data.entity.AllPlanner
import com.example.testapp.data.entity.ChildPlannerDB
import com.example.testapp.data.entity.PlannerDB
import com.example.testapp.databinding.FragmentPlannerBinding
import com.example.testapp.ui.BaseFragment
import com.example.testapp.ui.planner.child_planner.AddChildTicketDialogFragment
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch

class PlannerFragment : BaseFragment<FragmentPlannerBinding>(FragmentPlannerBinding::inflate) {

    private val plannerViewModel: PlannerViewModel by activityViewModels()
    private val plannerAdapter = PlannerAdapter()

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
            plannerAdapter.childDeleteListener = {
                plannerViewModel.removeChildTicket(it)
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

    private fun fetchPlanner() {
        lifecycleScope.launch {
            plannerViewModel.getPlanner()
                .combine(plannerViewModel.getChildPlanner()) { planners, childPlanners ->
                    val allPlanner = mutableListOf<AllPlanner>()
                    for (planner in planners) {
                        val childSortList = childPlanners.toMutableList()
                        childSortList.removeAll { it.parentId != planner.id }
                        allPlanner.add(
                            AllPlanner(
                                planner.id,
                                planner.body!!,
                                planner.date!!,
                                childSortList
                            )
                        )
                    }
                    return@combine allPlanner
                }.collectLatest {
                    plannerAdapter.items = it
                }
        }
    }
}