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

class PlannerAdapter : BaseRecyclerViewAdapter<PlannerDB, ItemPlannerBinding>() {

    var childAddListener: ((parentId : Int) -> Unit)? = null

    private val childPlannerAdapter = ChildPlannerAdapter()
    var childItems :MutableList<ChildPlannerDB> = mutableListOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }


    var checkListener = false

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ItemPlannerBinding
        get() = ItemPlannerBinding::inflate

    override fun bind(item: PlannerDB, view: View, position: Int) {
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
            rvPlanner.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = childPlannerAdapter
            }
//            item.child?.let {
//                childPlannerAdapter.items = it.toMutableList()
//            }
        }
    }

}