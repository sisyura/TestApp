package com.example.testapp.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.testapp.R

class NotificationsFragment : Fragment() {

    private val viewModel: NotificationsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                AuthInFirebase()
            }
        }
    }

    @Composable
    fun AuthInFirebase() {
        BoxWithConstraints(contentAlignment = Alignment.Center) {
            Button(onClick = {
                findNavController().navigate(R.id.action_navigation_notifications_to_loginFragment)
            }) {
                Text(text = getString(R.string.auth), fontSize = 25.sp)
            }
        }
    }
}