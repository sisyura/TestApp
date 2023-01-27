package com.example.testapp.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.testapp.R
import com.example.testapp.databinding.FragmentNotificationsBinding
import com.example.testapp.databinding.FragmentRegisterBinding
import com.example.testapp.ui.login.LoginFragment
import com.example.testapp.ui.notifications.NotificationsFragment
import com.example.testapp.utils.navigateTo
import com.google.firebase.auth.FirebaseAuth

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_register, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)
        auth = FirebaseAuth.getInstance()
        binding.apply {
            buttonRegister.setOnClickListener { register() }
            textViewLogin.setOnClickListener { goToLogin() }
        }
    }

    private fun register() {
        val email = binding.editTextEmailAddress.text.toString()
        val password = binding.editTextPassword.text.toString()

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                findNavController().navigate(R.id.action_registerFragment_to_navigation_notifications)
            }
        }.addOnFailureListener { exception ->
            Toast.makeText(context, exception.localizedMessage, Toast.LENGTH_LONG).show()
        }
    }

    private fun goToLogin() {
        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
    }
}