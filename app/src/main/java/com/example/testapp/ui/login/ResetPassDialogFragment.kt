package com.example.testapp.ui.login

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.testapp.databinding.DialogResetPasswordBinding
import com.example.testapp.ui.BaseDialogFragment
import com.example.testapp.ui.notifications.NotificationsViewModel
import com.google.firebase.auth.FirebaseAuth

class ResetPassDialogFragment :
    BaseDialogFragment<DialogResetPasswordBinding>(DialogResetPasswordBinding::inflate) {

    private val viewModel: NotificationsViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding.apply {
            btnCancel.setOnClickListener {
                viewModel.sendTag(LoginDialogFragment.TAG)
            }
            btnApply.setOnClickListener {
                context?.let { it1 -> resetMyPassword(etEmail.text.toString(), it1, auth) }
            }
        }
        return AlertDialog.Builder(requireActivity())
            .setView(binding.root)
            .create()
    }

    private fun resetMyPassword(email: String, context: Context, auth: FirebaseAuth) {
        try {
            auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    dismiss()
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

        const val TAG = "ResetPassDialogFragment"

        fun newInstance(): ResetPassDialogFragment {
            return ResetPassDialogFragment()
        }

    }
}