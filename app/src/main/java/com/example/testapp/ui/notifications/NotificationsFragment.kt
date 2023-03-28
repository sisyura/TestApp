package com.example.testapp.ui.notifications

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.testapp.R
import com.example.testapp.databinding.FragmentNotificationsBinding
import com.example.testapp.ui.BaseFragment

class NotificationsFragment : BaseFragment<FragmentNotificationsBinding>(FragmentNotificationsBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnAuth.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_notifications_to_loginFragment)
        }
    }
}