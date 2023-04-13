package com.example.testapp.ui.dashboard

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.testapp.data.entity.ItemCharacterDB
import com.example.testapp.databinding.ItemCharacterBinding
import com.example.testapp.ui.BaseRecyclerViewAdapter
import com.squareup.picasso.Picasso

class CharactersAdapter :
    BaseRecyclerViewAdapter<ItemCharacterDB, ItemCharacterBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ItemCharacterBinding
        get() = ItemCharacterBinding::inflate

    @SuppressLint("SetTextI18n")
    override fun bind(item: ItemCharacterDB, view: View, position: Int) {
        super.bind(item, view, position)
        binding.apply {
            Picasso.get().load(item.image).into(image)
            name.text = item.name
            body.text = "${item.species} id:${item.id}"
            view.setOnClickListener {
                listener?.invoke(items[position])
            }
        }
    }
}