package com.example.testapp.ui.notifications

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.testapp.R
import com.example.testapp.databinding.FragmentNotificationsBinding
import com.example.testapp.ui.BaseFragment
import com.google.firebase.auth.FirebaseAuth

class NotificationsFragment : BaseFragment<FragmentNotificationsBinding>(FragmentNotificationsBinding::inflate) {

    private lateinit var dialogLogin : Dialog
    private lateinit var dialogRegister : Dialog
    private lateinit var dialogReset : Dialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnAuth.setOnClickListener {
            login()
        }
    }

    private fun login() {
        dialogLogin = Dialog(requireActivity())
        dialogLogin.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogLogin.setContentView(R.layout.fragment_login)
        dialogLogin.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val btnLogin: Button = dialogLogin.findViewById(R.id.buttonLogin)
        val btnRegister: TextView = dialogLogin.findViewById(R.id.textViewRegister)
        val btnReset: TextView = dialogLogin.findViewById(R.id.tvReset)
        val etEmail: EditText = dialogLogin.findViewById(R.id.editTextEmailAddress)
        val etPassword: EditText = dialogLogin.findViewById(R.id.editTextPassword)
        btnLogin.setOnClickListener {
            login(etEmail.text.toString(), etPassword.text.toString())
        }
        btnRegister.setOnClickListener {
            dialogLogin.dismiss()
            registerDialog()
        }
        btnReset.setOnClickListener {
            dialogLogin.dismiss()
            resetDialog()
        }
        dialogLogin.show()
    }

    private fun registerDialog() {
        dialogRegister = Dialog(requireActivity())
        dialogRegister.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogRegister.setContentView(R.layout.fragment_register)
        dialogRegister.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val btnRegister: Button = dialogRegister.findViewById(R.id.buttonRegister)
        val btnLogin: TextView = dialogRegister.findViewById(R.id.textViewLogin)
        val etEmail: EditText = dialogRegister.findViewById(R.id.editTextRegisterEmailAddress)
        val etPassword: EditText = dialogRegister.findViewById(R.id.editTextRegisterPassword)
        val etConfirmPassword: EditText = dialogRegister.findViewById(R.id.editTextPasswordRepeat)
        btnRegister.setOnClickListener {
            register(etEmail.text.toString(), etPassword.text.toString(), etConfirmPassword.text.toString())
        }
        btnLogin.setOnClickListener {
            dialogRegister.dismiss()
            login()
        }
        dialogRegister.show()
    }

    private fun resetDialog() {
        dialogReset = Dialog(requireActivity())
        dialogReset.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogReset.setContentView(R.layout.dialog_reset_password)
        dialogReset.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val btnApply: Button = dialogReset.findViewById(R.id.btnApply)
        val btnCancel: Button = dialogReset.findViewById(R.id.btnCancel)
        val etEmail: EditText = dialogReset.findViewById(R.id.etEmail)
        btnCancel.setOnClickListener {
            dialogReset.dismiss()
            login()
        }
        btnApply.setOnClickListener {
            context?.let { it1 -> resetMyPassword(etEmail.text.toString(), it1, auth) }
        }
        dialogReset.show()
    }

    private fun login(email: String, password: String) {
        try {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    dialogLogin.dismiss()
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

    private fun register(email: String, password: String, passwordRepeat: String) {
        if (password == passwordRepeat) {
            try {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        dialogRegister.dismiss()
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
}