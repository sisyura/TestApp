package com.example.testapp.ui.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.testapp.R
import com.example.testapp.databinding.FragmentLoginBinding
import com.example.testapp.ui.BaseFragment


class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            buttonLogin.setOnClickListener {
                login(
                    editTextEmailAddress.text.toString(),
                    editTextPassword.text.toString()
                )
            }

            textViewRegister.setOnClickListener { goToRegister() }
            tvReset.setOnClickListener {
                context?.let { context ->
                    viewModel.resetPassword(
                        editTextEmailAddress.text.toString(),
                        context,
                        auth
                    )
                }
            }
        }
    }

    private fun login(email: String, password: String) {
        try {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    findNavController().navigate(R.id.action_loginFragment_to_navigation_notifications)
                }
            }.addOnFailureListener {
                Toast.makeText(context, "Проверьте правильность введенных данных.", Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(
                context,
                "Введите адрес электронной почты и пароль.",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun goToRegister() {
        findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
    }
}