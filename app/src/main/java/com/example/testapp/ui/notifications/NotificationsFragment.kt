package com.example.testapp.ui.notifications

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.example.testapp.databinding.FragmentNotificationsBinding
import com.example.testapp.ui.BaseFragment
import com.example.testapp.ui.login.LoginDialogFragment
import com.example.testapp.ui.login.ResetPassDialogFragment
import com.example.testapp.ui.register.RegisterDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationsFragment :
    BaseFragment<FragmentNotificationsBinding>(FragmentNotificationsBinding::inflate) {

    //    private lateinit var dialogLogin : Dialog
//    private lateinit var dialogRegister : Dialog
//    private lateinit var dialogReset : Dialog
    private val viewModel: NotificationsViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnAuth.setOnClickListener {
            LoginDialogFragment.newInstance().show(childFragmentManager, LoginDialogFragment.TAG)
        }
        viewModel.getTag().observe(viewLifecycleOwner) {

            when (it) {
                RegisterDialogFragment.TAG -> {
                    (childFragmentManager.findFragmentByTag(LoginDialogFragment.TAG) as? LoginDialogFragment)?.dismiss()
                    RegisterDialogFragment.newInstance().show(childFragmentManager, RegisterDialogFragment.TAG)
                }
                LoginDialogFragment.TAG -> {
                    (childFragmentManager.findFragmentByTag(RegisterDialogFragment.TAG) as? RegisterDialogFragment)?.dismiss()
                    (childFragmentManager.findFragmentByTag(ResetPassDialogFragment.TAG) as? ResetPassDialogFragment)?.dismiss()
                    LoginDialogFragment.newInstance().show(childFragmentManager, LoginDialogFragment.TAG)
                }
                ResetPassDialogFragment.TAG -> {
                    (childFragmentManager.findFragmentByTag(LoginDialogFragment.TAG) as? LoginDialogFragment)?.dismiss()
                    ResetPassDialogFragment.newInstance().show(childFragmentManager, ResetPassDialogFragment.TAG)
                }
            }
        }
    }

    /* private fun login() {
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
     }*/
}