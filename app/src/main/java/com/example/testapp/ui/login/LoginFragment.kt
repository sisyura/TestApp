package com.example.testapp.ui.login

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.testapp.R
import com.example.testapp.databinding.FragmentLoginBinding
import com.example.testapp.ui.BaseFragment
import com.google.firebase.auth.FirebaseAuth


class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: LoginViewModel by viewModels()

    private lateinit var dialog : Dialog

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
                dialog = Dialog(requireActivity())
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setContentView(R.layout.dialog_reset_password)
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                val btnApply: Button = dialog.findViewById(R.id.btnApply)
                val btnCancel: Button = dialog.findViewById(R.id.btnCancel)
                val etEmail: EditText = dialog.findViewById(R.id.etEmail)
                btnCancel.setOnClickListener {
                    dialog.dismiss()
                }
                btnApply.setOnClickListener {
                    context?.let { it1 -> resetMyPassword(etEmail.text.toString(), it1, auth) }
                }
                dialog.show()
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

    private fun resetMyPassword(email: String, context: Context, auth: FirebaseAuth) {
        try {
            auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    dialog.dismiss()
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
}