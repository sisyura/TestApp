package com.example.testapp.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.R
import com.example.testapp.databinding.FragmentHomeBinding
import com.example.testapp.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()
    private val characterAdapter = CharacterPagerAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.characterRV.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = characterAdapter
            fetchCharacterItems()
        }
        characterAdapter.listener = {
            viewModel.isSaved(it.id)
            it.isSaved = viewModel.isSaved
            val bundle = bundleOf("character" to it)
            findNavController().findDestination(R.id.newsDetailFragment)?.label = it.name
            findNavController().navigate(R.id.action_navigation_home_to_newsDetailFragment, bundle)
        }
        binding.apply {
            swipeRefreshLayout.setOnRefreshListener {
                fetchCharacterItems()

                if (context?.let { viewModel.isOnline(it) } == false) {
                    Toast.makeText(context, getString(R.string.no_connection), Toast.LENGTH_LONG)
                        .show()
                }
                swipeRefreshLayout.isRefreshing = false
            }
            if (context?.let { viewModel.isOnline(it) } == false) {
                tvOffline.visibility = View.VISIBLE
                characterRV.visibility = View.GONE
            } else {
                tvOffline.visibility = View.GONE
                characterRV.visibility = View.VISIBLE
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getCharactersListFlow().onEach {

        }
    }

    private fun fetchCharacterItems() {
        lifecycleScope.launch {
            viewModel.getCharactersListFlow().distinctUntilChanged().collectLatest {
                characterAdapter.submitData(it)
                binding.characterRV.invalidate()

            }
        }
    }
}