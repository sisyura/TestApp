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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.compose.AsyncImage

import com.example.testapp.data.NewsItem

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private var newsList : List<NewsItem>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                newsList?.let { Conversation(it) }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.newsList.observe(viewLifecycleOwner) {
            newsList = it
        }
    }

    @Composable
    fun MessageCard(news: NewsItem) {
        Row(modifier = Modifier.padding(8.dp)) {
            AsyncImage(
                model = news.image,
                contentDescription = "image",
                modifier = Modifier
                    .size(55.dp)
                    .clip(CircleShape)
                    .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))

            var isExpanded by remember { mutableStateOf(false) }
            val surfaceColor by animateColorAsState(
                if (isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface,
            )

            Column(modifier = Modifier.clickable { isExpanded = !isExpanded}) {
                Text(
                    text = news.title,
                    color = MaterialTheme.colors.secondaryVariant,
                    style = MaterialTheme.typography.subtitle2
                )
                Spacer(modifier = Modifier.height(4.dp))
                Surface(
                    shape = MaterialTheme.shapes.small,
                    elevation = 1.dp,
                    color = surfaceColor,
                    modifier = Modifier.animateContentSize().padding(1.dp)
                ) {
                    news.body?.let {
                        Text(
                            text = it,
                            modifier = Modifier.padding(all = 4.dp),
                            style = MaterialTheme.typography.body2,
                            maxLines = if (isExpanded) Int.MAX_VALUE else 1
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun Conversation(allNews: List<NewsItem>) {
        LazyColumn(modifier = Modifier.padding(bottom = 56.dp)) {
            items(allNews) { news ->
                MessageCard(news)
            }
        }
    }

    @Preview
    @Composable
    fun PreviewConversation() {
        newsList?.let {
            LazyColumn(modifier = Modifier.padding(bottom = 56.dp)) {
                items(it) { news ->
                    MessageCard(news)
                }
            }
        }
    }
}