package com.example.testapp.ui.register

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
import androidx.navigation.fragment.findNavController
import com.example.testapp.R
import com.google.firebase.auth.FirebaseAuth

class RegisterFragment : Fragment() {

    private var _auth: FirebaseAuth? = null
    private val auth get() = requireNotNull(_auth)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                RegisterView()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _auth = FirebaseAuth.getInstance()

    }

    private fun register(email: String, password: String) {

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
    }

    private fun goToLogin() {
        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
    }

    @Preview
    @Composable
    fun RegisterView() {

        var login by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        BoxWithConstraints(contentAlignment = Alignment.Center) {
            ConstraintLayout {

                val (editTextEmailAddress, editTextPassword, buttonRegister, clLogin) = createRefs()

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
                    modifier = Modifier.constrainAs(buttonRegister) {
                        centerHorizontallyTo(parent)
                        top.linkTo(editTextPassword.bottom, margin = 8.dp)
                    }) {
                    Button(
                        onClick = {
                            register(login, password)
                        }
                    ) {
                        Text("Создать", fontSize = 25.sp)
                    }
                }

                ConstraintLayout(modifier = Modifier.constrainAs(clLogin) {
                    centerHorizontallyTo(parent)
                    top.linkTo(buttonRegister.bottom, margin = 8.dp)
                }) {

                    val (registerText, registerBtn) = createRefs()

                    Column(horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.constrainAs(registerText) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                        }) {
                        Text(text = "Уже есть аккаунт?")
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.constrainAs(registerBtn) {
                            top.linkTo(parent.top)
                            start.linkTo(registerText.end, margin = 4.dp)
                        }) {
                        Text(text = "Войти", Modifier.clickable {
                            context?.let { goToLogin() }
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