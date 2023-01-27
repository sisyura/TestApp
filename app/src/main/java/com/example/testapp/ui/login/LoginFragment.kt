package com.example.testapp.ui.login

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.testapp.R
import com.example.testapp.databinding.FragmentLoginBinding
import com.example.testapp.ui.notifications.NotificationsFragment
import com.example.testapp.ui.register.RegisterFragment
import com.example.testapp.utils.navigateTo
import com.google.firebase.auth.FirebaseAuth


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_login, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        auth = FirebaseAuth.getInstance()
        binding.apply {
            buttonLogin.setOnClickListener { login() }
            textViewRegister.setOnClickListener { goToRegister() }
            tvReset.setOnClickListener { resetPassword(editTextEmailAddress.text.toString()) }
        }
    }

    private fun resetPassword(email: String) {
        try {
            auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        context,
                        "Инструкция для восстановления пароля отправлена вам на почту.",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        context,
                        "Проверьте правильность введенных данных.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(
                context,
                "Введите адрес электронной почты.",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun login() {
        val email = binding.editTextEmailAddress.text.toString()
        val password = binding.editTextPassword.text.toString()

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                findNavController().navigate(R.id.action_loginFragment_to_navigation_notifications)
            }
        }.addOnFailureListener { exception ->
            Toast.makeText(context, exception.localizedMessage, Toast.LENGTH_LONG).show()
        }
    }

    private fun goToRegister() {
        findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
    }
}