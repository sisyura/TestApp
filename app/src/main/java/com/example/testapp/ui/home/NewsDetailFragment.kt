package com.example.testapp.ui.home

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import coil.compose.AsyncImage
import com.example.testapp.data.entity.ItemCharacter

class NewsDetailFragment : Fragment() {

    private var character: ItemCharacter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                character?.let {
                    CharacterItem(character = it)
                }
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        character = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            arguments?.get("character") as ItemCharacter?
        } else {
            arguments?.getParcelable("character", ItemCharacter::class.java)
        }
    }

    @Composable
    fun CharacterItem(character: ItemCharacter) {
        ConstraintLayout {
            val (avatar, name, nameCh, status, statusCh, species, speciesCh, gender, genderCh, origin, originCh, location, locationCh) = createRefs()

            AsyncImage(
                model = character.image,
                contentDescription = "avatar",
                modifier = Modifier
                    .fillMaxWidth(0.96f)
                    .clip(shape = RoundedCornerShape(4.dp))
                    .constrainAs(avatar) {
                        top.linkTo(parent.top, margin = 8.dp)
                        start.linkTo(parent.start, margin = 12.dp)
                        end.linkTo(parent.end, margin = 12.dp)
                    }
            )


            Text(
                modifier = Modifier.constrainAs(name) {
                    top.linkTo(avatar.bottom, margin = 8.dp)
                    start.linkTo(parent.start, margin = 8.dp)
                },
                text = "Имя:"
            )

            character.name?.let {
                Text(
                    modifier = Modifier.constrainAs(nameCh) {
                        top.linkTo(avatar.bottom, margin = 8.dp)
                        start.linkTo(name.end, margin = 8.dp)
                    },
                    text = it
                )
            }

            Text(
                modifier = Modifier.constrainAs(gender) {
                    top.linkTo(name.bottom, margin = 8.dp)
                    start.linkTo(parent.start, margin = 8.dp)
                },
                text = "Гендер:"
            )

            character.gender?.let {
                Text(
                    modifier = Modifier.constrainAs(genderCh) {
                        top.linkTo(name.bottom, margin = 8.dp)
                        start.linkTo(gender.end, margin = 8.dp)
                    },
                    text = it
                )
            }

            Text(
                modifier = Modifier.constrainAs(status) {
                    top.linkTo(gender.bottom, margin = 8.dp)
                    start.linkTo(parent.start, margin = 8.dp)
                },
                text = "Статус:"
            )

            character.status?.let {
                Text(
                    modifier = Modifier.constrainAs(statusCh) {
                        top.linkTo(gender.bottom, margin = 8.dp)
                        start.linkTo(status.end, margin = 8.dp)
                    },
                    text = it
                )
            }

            Text(
                modifier = Modifier.constrainAs(species) {
                    top.linkTo(status.bottom, margin = 8.dp)
                    start.linkTo(parent.start, margin = 8.dp)
                },
                text = "Вид:"
            )

            character.species?.let {
                Text(
                    modifier = Modifier.constrainAs(speciesCh) {
                        top.linkTo(status.bottom, margin = 8.dp)
                        start.linkTo(species.end, margin = 8.dp)
                    },
                    text = it
                )
            }

            Text(
                modifier = Modifier.constrainAs(origin) {
                    top.linkTo(species.bottom, margin = 8.dp)
                    start.linkTo(parent.start, margin = 8.dp)
                },
                text = "Место рождения:"
            )

            character.origin?.name?.let {
                Text(
                    modifier = Modifier.constrainAs(originCh) {
                        top.linkTo(species.bottom, margin = 8.dp)
                        start.linkTo(origin.end, margin = 8.dp)
                    },
                    text = it
                )
            }

            Text(
                modifier = Modifier.constrainAs(location) {
                    top.linkTo(origin.bottom, margin = 8.dp)
                    start.linkTo(parent.start, margin = 8.dp)
                },
                text = "Местонахождение:"
            )

            character.location?.name?.let {
                Text(
                    modifier = Modifier.constrainAs(locationCh) {
                        top.linkTo(origin.bottom, margin = 8.dp)
                        start.linkTo(location.end, margin = 8.dp)
                    },
                    text = it
                )
            }
        }

    }
}