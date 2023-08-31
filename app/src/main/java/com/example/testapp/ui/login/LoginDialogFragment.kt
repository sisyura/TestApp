package com.example.testapp.ui.login

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.testapp.databinding.FragmentLoginBinding
import com.example.testapp.ui.BaseDialogFragment
import com.example.testapp.ui.notifications.NotificationsViewModel
import com.example.testapp.ui.register.RegisterDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginDialogFragment :
    BaseDialogFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    interface GoToRegisterClickListener {
        fun goToRegisterClick()
    }

    interface GoToResetPassClickListener {
        fun goToResetPassClick()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding.apply {
            buttonLogin.setOnClickListener {
                login(
                    editTextEmailAddress.text.toString(),
                    editTextPassword.text.toString()
                )
            }
            textViewRegister.setOnClickListener {
                (parentFragment as GoToRegisterClickListener).goToRegisterClick()
                dismiss()
            }
            tvReset.setOnClickListener {
                (parentFragment as GoToResetPassClickListener).goToResetPassClick()
                dismiss()
            }
        }
        return AlertDialog.Builder(requireActivity())
            .setView(binding.root)
            .create()
    }

    private fun login(email: String, password: String) {
        try {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    dismiss()
                }
            }.addOnFailureListener {
                Toast.makeText(
                    context,
                    "Проверьте правильность введенных данных.",
                    Toast.LENGTH_LONG
                ).show()
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

    companion object {

        const val TAG = "LoginDialogFragment"

        fun newInstance(): LoginDialogFragment {
            return LoginDialogFragment()
        }

    }
}