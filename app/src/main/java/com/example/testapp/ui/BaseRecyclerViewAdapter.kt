package com.example.testapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseRecyclerViewAdapter<T, VB: ViewBinding> : RecyclerView.Adapter<BaseRecyclerViewAdapter<T, VB>.ViewHolder>() {

    private var _binding: VB? = null

    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB

    protected val binding: VB
        get() = requireNotNull(_binding)

    var listener: ((item: T) -> Unit)?= null

    open var items: MutableList<T> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        _binding = bindingInflater.invoke(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(_binding as ViewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        holder.bind(position)
    }

    open inner class ViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
        open fun bind(position: Int) {
            bind(items[position], itemView, position)
            itemView.setOnClickListener{
                listener?.invoke(items[position])
            }
        }
    }

    open fun bind(item: T, view: View, position: Int) { }

}