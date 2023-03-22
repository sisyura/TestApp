package com.example.testapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.testapp.R

import com.example.testapp.data.NewsItem
import com.example.testapp.data.entity.ItemCharacter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                Conversation()
            }
        }
    }

    @Composable
    fun CharacterCard(character: ItemCharacter) {
        Row(modifier = Modifier
            .padding(8.dp)
            .clickable {
                val bundle = bundleOf("character" to character)
                findNavController().navigate(R.id.newsDetailFragment, bundle)
            }
        ) {
            AsyncImage(
                model = character.image,
                contentDescription = "image",
                modifier = Modifier
                    .size(55.dp)
                    .clip(CircleShape)
                    .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier.weight(1f),
            ) {
                character.name?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colors.secondaryVariant,
                        style = MaterialTheme.typography.subtitle2
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Surface(
                    shape = MaterialTheme.shapes.small,
                    elevation = 1.dp
                ) {
                    character.species?.let {
                        Text(
                            text = "$it id: ${character.id}",
                            modifier = Modifier.padding(all = 4.dp),
                            style = MaterialTheme.typography.body2
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun Conversation() {
        val characterItems = viewModel.charactersItems.collectAsLazyPagingItems()
        LazyColumn(modifier = Modifier.padding(bottom = 56.dp)) {
            items(
                count = characterItems.itemCount,
                key = { index ->
                    val character = characterItems[index]
                    character?.id ?: ""
                }
            ) { index ->
                val character = characterItems[index] ?: return@items
                CharacterCard(character = character)
            }
        }
    }
}