package com.example.testapp.ui.home.NewsDetail

import android.os.Build
import android.os.Bundle
import android.view.View
import com.example.testapp.databinding.FragmentCharacterImageDetailBinding
import com.example.testapp.ui.BaseFragment
import com.squareup.picasso.Picasso

class CharacterImageDetailFragment: BaseFragment<FragmentCharacterImageDetailBinding>(FragmentCharacterImageDetailBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val characterImage = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            requireNotNull(arguments?.get("characterImage") as String?)
        } else {
            requireNotNull(arguments?.getParcelable("characterImage", String::class.java))
        }
        Picasso.get().load(characterImage).into(binding.image)
    }
}