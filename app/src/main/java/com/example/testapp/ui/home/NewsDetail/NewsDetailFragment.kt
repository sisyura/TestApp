package com.example.testapp.ui.home.NewsDetail

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.testapp.R
import com.example.testapp.data.entity.ItemCharacter
import com.example.testapp.databinding.FragmentNewsDetailBinding
import com.example.testapp.ui.BaseFragment
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsDetailFragment : BaseFragment<FragmentNewsDetailBinding>(FragmentNewsDetailBinding::inflate) {

    private lateinit var character: ItemCharacter
    private val viewModel: NewsDetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        character = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            requireNotNull(arguments?.get("character") as ItemCharacter)
        } else {
            requireNotNull(arguments?.getParcelable("character", ItemCharacter::class.java))
        }
        binding.apply {
            Picasso.get().load(character.image).into(image)
            name.text = character.name
            gender.text = character.gender
            status.text = character.status
            species.text = character.species
            born.text = character.origin?.name
            location.text = character.location?.name
            if (character.isSaved) {
                saveBtn.setImageResource(R.drawable.ic_save)
            } else {
                saveBtn.setImageResource(R.drawable.ic_not_save)
            }
            saveBtn.setOnClickListener {
                character.isSaved = !character.isSaved
                viewModel.saveCharacter(character)
                if (character.isSaved) {
                    saveBtn.setImageResource(R.drawable.ic_save)
                } else {
                    saveBtn.setImageResource(R.drawable.ic_not_save)
                }
            }
        }
    }
}