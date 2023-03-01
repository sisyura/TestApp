package com.example.testapp.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.testapp.R
import com.google.firebase.auth.FirebaseAuth


class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()
    private var _auth: FirebaseAuth? = null
    private val auth get() = requireNotNull(_auth)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                LoginView()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _auth = FirebaseAuth.getInstance()

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

    @Preview
    @Composable
    fun LoginView() {

        var login by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        BoxWithConstraints(contentAlignment = Alignment.Center) {
            ConstraintLayout {

                val (editTextEmailAddress, editTextPassword, buttonLogin, clResetPass, clRegister) = createRefs()

                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.constrainAs(editTextEmailAddress) {
                        centerHorizontallyTo(parent)
                        top.linkTo(parent.top, margin = 8.dp)
                    }) {
                    OutlinedTextField(
                        value = login,
                        onValueChange = {
                            login = it
                        },
                        trailingIcon = {
                            if (isVisible) {
                                IconButton(
                                    onClick = { login = "" }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Clear,
                                        contentDescription = "Clear"
                                    )
                                }
                            }
                        },
                        label = {
                            Text("Email")
                        },
                        placeholder = {
                            Text("Введите email")
                        },
                        singleLine = true
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.constrainAs(editTextPassword) {
                        centerHorizontallyTo(parent)
                        top.linkTo(editTextEmailAddress.bottom, margin = 8.dp)
                    }) {
                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            password = it
                        },
                        trailingIcon = {
                            if (isVisible) {
                                IconButton(
                                    onClick = { password = "" }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Clear,
                                        contentDescription = "Clear"
                                    )
                                }
                            }
                        },
                        label = {
                            Text("Пароль")
                        },
                        placeholder = {
                            Text("Введите пароль")
                        },
                        singleLine = true
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.constrainAs(buttonLogin) {
                        centerHorizontallyTo(parent)
                        top.linkTo(editTextPassword.bottom, margin = 8.dp)
                    }) {
                    Button(
                        onClick = {
                            login(login, password)
                        }
                    ) {
                        Text("Войти", fontSize = 25.sp)
                    }
                }

                ConstraintLayout(modifier = Modifier.constrainAs(clResetPass) {
                    centerHorizontallyTo(parent)
                    top.linkTo(buttonLogin.bottom, margin = 8.dp)
                }) {

                    val (resetText, resetBtn) = createRefs()

                    Column(horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.constrainAs(resetText) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                        }) {
                        Text(text = "Забыли пароль?")
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.constrainAs(resetBtn) {
                            top.linkTo(parent.top)
                            start.linkTo(resetText.end, margin = 4.dp)
                        }) {
                        Text(text = "Восстановить", Modifier.clickable {
                            context?.let { viewModel.resetPassword(login, it, auth) }
                        },
                            fontWeight = FontWeight.SemiBold)
                    }

                }

                ConstraintLayout(modifier = Modifier.constrainAs(clRegister) {
                    centerHorizontallyTo(parent)
                    top.linkTo(clResetPass.bottom, margin = 8.dp)
                }) {

                    val (registerText, registerBtn) = createRefs()

                    Column(horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.constrainAs(registerText) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                        }) {
                        Text(text = "Нет аккаунта?")
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.constrainAs(registerBtn) {
                            top.linkTo(parent.top)
                            start.linkTo(registerText.end, margin = 4.dp)
                        }) {
                        Text(text = "Зарегистрироваться", Modifier.clickable {
                            context?.let { goToRegister() }
                        },
                            fontWeight = FontWeight.SemiBold)
                    }

                }

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _auth = null
    }
}