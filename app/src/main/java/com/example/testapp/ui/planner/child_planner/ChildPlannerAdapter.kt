package com.example.testapp.ui.planner.child_planner

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.testapp.data.entity.ChildPlannerDB
import com.example.testapp.databinding.ItemPlannerChildBinding
import com.example.testapp.ui.BaseRecyclerViewAdapter

class ChildPlannerAdapter : BaseRecyclerViewAdapter<ChildPlannerDB, ItemPlannerChildBinding>() {
    var checkListener = false

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ItemPlannerChildBinding
        get() = ItemPlannerChildBinding::inflate

    override fun bind(item: ChildPlannerDB, view: View, position: Int) {
        super.bind(item, view, position)
        binding.apply {
            tvPlanner.text = item.body
            checkBox.setOnClickListener {
                checkListener = checkBox.isChecked
                listener?.invoke(item)
            }
        }
    }
}