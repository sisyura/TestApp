package com.example.testapp.ui.register

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.testapp.R
import com.example.testapp.databinding.FragmentRegisterBinding
import com.example.testapp.ui.BaseFragment

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            buttonRegister.setOnClickListener {
                register(
                    editTextRegisterEmailAddress.text.toString(),
                    editTextRegisterPassword.text.toString(),
                    editTextPasswordRepeat.text.toString()
                )
            }

            textViewLogin.setOnClickListener { goToLogin() }
        }
    }

    private fun register(email: String, password: String, passwordRepeat: String) {
        if (password == passwordRepeat) {
            try {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        findNavController().navigate(R.id.action_registerFragment_to_navigation_notifications)
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
        } else {
            Toast.makeText(
                context,
                "Пароли должны совпадать.",
                Toast.LENGTH_LONG
            ).show()
        }

    }

    private fun goToLogin() {
        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
    }
}