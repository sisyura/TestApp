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
import androidx.fragment.app.viewModels
import com.example.testapp.R
import com.example.testapp.databinding.FragmentLoginBinding
import com.example.testapp.ui.BaseDialogFragment
import com.example.testapp.ui.notifications.NotificationsViewModel
import com.example.testapp.ui.register.RegisterDialogFragment
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginDialogFragment :
    BaseDialogFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private lateinit var dialogReset: Dialog

    private val viewModel : NotificationsViewModel by activityViewModels()

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
                viewModel.sendTag(TAG)
            }
            tvReset.setOnClickListener {
                dialogReset = Dialog(requireActivity())
                dialogReset.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialogReset.setContentView(R.layout.dialog_reset_password)
                dialogReset.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                val btnApply: Button = dialogReset.findViewById(R.id.btnApply)
                val btnCancel: Button = dialogReset.findViewById(R.id.btnCancel)
                val etEmail: EditText = dialogReset.findViewById(R.id.etEmail)
                btnCancel.setOnClickListener {
                    dialogReset.dismiss()
                }
                btnApply.setOnClickListener {
                    context?.let { it1 -> resetMyPassword(etEmail.text.toString(), it1, auth) }
                }
                dialogReset.show()
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

    private fun resetMyPassword(email: String, context: Context, auth: FirebaseAuth) {
        try {
            auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    dialogReset.dismiss()
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

    companion object {

        const val TAG = "LoginDialogFragment"

        fun newInstance(): LoginDialogFragment {
            return LoginDialogFragment()
        }

    }
}