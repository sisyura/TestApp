package com.example.testapp.ui.register

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.testapp.R
import com.example.testapp.databinding.FragmentRegisterBinding
import com.example.testapp.ui.BaseDialogFragment
import com.example.testapp.ui.home.HomeViewModel
import com.example.testapp.ui.login.LoginDialogFragment
import com.example.testapp.ui.notifications.NotificationsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterDialogFragment : BaseDialogFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val viewModel : NotificationsViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding.apply {
            buttonRegister.setOnClickListener {
                register(
                    editTextRegisterEmailAddress.text.toString(),
                    editTextRegisterPassword.text.toString(),
                    editTextPasswordRepeat.text.toString()
                )
            }

            textViewLogin.setOnClickListener {
                viewModel.sendTag(TAG)
            }
        }
        return AlertDialog.Builder(requireActivity())
            .setView(binding.root)
            .create()
    }

    private fun register(email: String, password: String, passwordRepeat: String) {
        if (password == passwordRepeat) {
            try {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        dismiss()
                        Toast.makeText(context, "Регистрация прошла успешно.", Toast.LENGTH_LONG).show()
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

    companion object {

        const val TAG = "RegisterDialogFragment"

        fun newInstance(): RegisterDialogFragment {
            return RegisterDialogFragment()
        }

    }
}