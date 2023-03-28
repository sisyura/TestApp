package com.example.testapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.data.entity.ItemCharacter
import com.example.testapp.databinding.ItemCharacterBinding
import com.squareup.picasso.Picasso

class CharacterPagerAdapter: PagingDataAdapter<ItemCharacter, CharacterPagerAdapter.CharacterViewHolder>(CharacterComparator) {

    var listener: ((item: ItemCharacter) -> Unit)? = null

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = getItem(position)
        Picasso.get().load(character?.image).into(holder.view.image)
        holder.view.apply {
            name.text = character?.name
            body.text = "${character?.species} id:${character?.id}"
        }
        holder.itemView.setOnClickListener{
            if (character != null) {
                listener?.invoke(character)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCharacterBinding.inflate(inflater, parent, false)
        return CharacterViewHolder(binding)
    }

    class CharacterViewHolder(val view: ItemCharacterBinding): RecyclerView.ViewHolder(view.root) {

    }

    object CharacterComparator: DiffUtil.ItemCallback<ItemCharacter>() {
        override fun areItemsTheSame(oldItem: ItemCharacter, newItem: ItemCharacter): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ItemCharacter, newItem: ItemCharacter): Boolean {
            return oldItem == newItem
        }
    }
}