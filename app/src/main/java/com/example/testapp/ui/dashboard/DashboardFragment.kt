package com.example.testapp.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.testapp.R
import com.example.testapp.data.entity.CharacterLocation
import com.example.testapp.data.entity.CharacterOrigin
import com.example.testapp.data.entity.ItemCharacter
import com.example.testapp.data.entity.ItemCharacterDB
import com.example.testapp.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : Fragment() {


    private val viewModel: DashboardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                Conversation()
            }
        }
        viewModel.apply {  }
    }

    @Composable
    fun CharacterCard(characterDB: ItemCharacterDB) {
        Row(modifier = Modifier
            .padding(8.dp)
            .clickable {
                val character = ItemCharacter(characterDB.id, characterDB.name, characterDB.status, characterDB.species, characterDB.gender, CharacterOrigin(characterDB.origin), CharacterLocation(characterDB.location), characterDB.image, true)
                val bundle = bundleOf("character" to character)
                findNavController().navigate(R.id.action_navigation_dashboard_to_newsDetailFragment2, bundle)
            }
        ) {
            AsyncImage(
                model = characterDB.image,
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
                characterDB.name?.let {
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
                    characterDB.species?.let {
                        Text(
                            text = "$it id: ${characterDB.id}",
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
        val characterItems by viewModel.allCharacter.collectAsState(initial = emptyList())
        LazyColumn(modifier = Modifier.padding(bottom = 56.dp)) {
            items(
                count = characterItems.count(),
                key = { index ->
                    val character = characterItems[index]
                    character.id ?: ""
                }
            ) { index ->
                val character = characterItems[index] ?: return@items
                CharacterCard(characterDB = character)
            }
        }
    }

   /* private val viewModel: DashboardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                Sum()
            }
        }
    }

    @Preview
    @Composable
    fun Sum() {

        var text by remember { mutableStateOf("") }
        var showError by remember { mutableStateOf(true) }
        var message by remember { mutableStateOf("") }
        val isVisible by remember { derivedStateOf { message.isNotBlank() } }

        BoxWithConstraints(
            contentAlignment = Alignment.Center
        ) {
            ConstraintLayout {
                val (buttonElement, textElement, textFieldElement) = createRefs()

                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.constrainAs(textElement) {
                        centerHorizontallyTo(parent)
                        top.linkTo(parent.top)
                    }) {
                    Text(text = text)
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.constrainAs(textFieldElement) {
                        centerHorizontallyTo(parent)
                        top.linkTo(textElement.bottom, margin = 8.dp)
                    }) {
                    OutlinedTextField(
                        value = message,
                        onValueChange = {
                            message = it
                            showError = viewModel.isValidEmail(it) == null
                        },
                        isError = showError,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        trailingIcon = {
                            if (isVisible) {
                                IconButton(
                                    onClick = { message = "" }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Clear,
                                        contentDescription = "Clear"
                                    )
                                }
                            }
                        },
                        label = {
                            Text("Сумма")
                        },
                        placeholder = {
                            Text("Введите сумму")
                        },
                        singleLine = true
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.constrainAs(buttonElement) {
                        centerHorizontallyTo(parent)
                        top.linkTo(textFieldElement.bottom, margin = 8.dp)
                    }) {
                    Button(
                        onClick = {
                            text = "$message руб."
                            message = ""
                        },
                        enabled = !showError
                    ) {
                        Text("Записать", fontSize = 25.sp)
                    }
                }

            }
        }
    }*/
}