package com.example.testapp.ui.notifications

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.testapp.ui.BaseFragment
import com.example.testapp.R
import com.example.testapp.databinding.FragmentNotificationsBinding

class NotificationsFragment : BaseFragment<FragmentNotificationsBinding>(FragmentNotificationsBinding::inflate) {

    private val viewModel: NotificationsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.text.observe(viewLifecycleOwner) {
            binding.textNotifications.text = it
        }
        binding.btnAuth.setOnClickListener{
            findNavController().navigate(R.id.action_navigation_notifications_to_loginFragment)
        }
    }
}