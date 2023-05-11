package com.example.testapp.ui.planner

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.data.entity.AllPlanner
import com.example.testapp.data.entity.ChildPlannerDB
import com.example.testapp.data.entity.PlannerDB
import com.example.testapp.databinding.ItemPlannerBinding
import com.example.testapp.ui.BaseRecyclerViewAdapter
import com.example.testapp.ui.planner.child_planner.ChildPlannerAdapter

class PlannerAdapter : BaseRecyclerViewAdapter<AllPlanner, ItemPlannerBinding>() {

    var childAddListener: ((parentId : Int) -> Unit)? = null
    var childDeleteListener: ((childId : Int) -> Unit)? = null

    var checkListener = false

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ItemPlannerBinding
        get() = ItemPlannerBinding::inflate

    override fun bind(item: AllPlanner, view: View, position: Int) {
        super.bind(item, view, position)
        binding.apply {
            tvPlanner.text = item.body
            checkBox.setOnClickListener {
                checkListener = checkBox.isChecked
                listener?.invoke(item)
            }
            addChildBtn.setOnClickListener {
                childAddListener?.invoke(item.id)
            }
            val childPlannerAdapter = ChildPlannerAdapter()
            rvPlanner.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = childPlannerAdapter
            }
            if (item.child.isNotEmpty()) {
                childPlannerAdapter.items = item.child.toMutableList()
            }
            childPlannerAdapter.listener = {
                if (childPlannerAdapter.checkListener)
                    childDeleteListener?.invoke(it.id)
            }
        }
    }

}