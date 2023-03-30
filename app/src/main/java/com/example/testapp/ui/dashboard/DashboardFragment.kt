package com.example.testapp.ui.dashboard

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.R
import com.example.testapp.data.entity.CharacterLocation
import com.example.testapp.data.entity.CharacterOrigin
import com.example.testapp.data.entity.ItemCharacter
import com.example.testapp.databinding.FragmentDashboardBinding
import com.example.testapp.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DashboardFragment :
    BaseFragment<FragmentDashboardBinding>(FragmentDashboardBinding::inflate) {

    private val viewModel: DashboardViewModel by viewModels()
    private val charactersAdapter = CharactersAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.characterRV.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = charactersAdapter
            fetchCharacterItems()
        }
        charactersAdapter.listener = {
            val character = ItemCharacter(
                it.id,
                it.name,
                it.status,
                it.species,
                it.gender,
                CharacterOrigin(it.origin),
                CharacterLocation(it.location),
                it.image,
                true
            )
            val bundle = bundleOf("character" to character)
            findNavController().findDestination(R.id.newsDetailFragment)?.label = character.name
            findNavController().navigate(
                R.id.action_navigation_dashboard_to_newsDetailFragment2,
                bundle
            )
        }
    }

    private fun fetchCharacterItems() {
        lifecycleScope.launch {
            viewModel.allCharacter.distinctUntilChanged().collectLatest {
                charactersAdapter.items = it.toMutableList()
            }
        }
    }
}