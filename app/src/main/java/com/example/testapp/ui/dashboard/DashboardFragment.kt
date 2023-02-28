package com.example.testapp.ui.dashboard

import android.R.attr.label
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import java.util.regex.Pattern


class DashboardFragment : Fragment() {

    private val viewModel: DashboardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                Sum()
            }
        }
    }


//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//            val sum = "${etSum.text.toString().replace(",", " ")} руб."
//            btnApply.setOnClickListener { textDashboard.text = sum }
//
//    }

    @Preview
    @Composable
    fun Sum() {

        fun isValidEmail(currency: String?) =
            currency?.let {
                Pattern
                    .compile(
                        "^[0-9]{9}+\\.[0-9]{2}",
                        Pattern.CASE_INSENSITIVE
                    ).matcher(it).find()
            }
        var text by remember { mutableStateOf("") }
        var showError by remember { mutableStateOf(false) }
        var message by remember { mutableStateOf("") }
        val isVisible by remember {
            derivedStateOf {
                message.isNotBlank()
            }
        }

        BoxWithConstraints(
            contentAlignment = Alignment.Center
        ) {
            ConstraintLayout() {
                val (buttonElement, textElement, textFieldElement) = createRefs()

                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.constrainAs(textElement) {
                        centerHorizontallyTo(parent)
                        top.linkTo(parent.top)
                    }) {
                    Text(text = text)
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.constrainAs(textFieldElement) {
                        centerHorizontallyTo(parent)
                        top.linkTo(textElement.bottom, margin = 8.dp)
                    }) {
                    OutlinedTextField(
                        value = String(),
                        onValueChange = {
                            message = it
                            showError = !isValidEmail(it)!!
                        },
                        isError = showError,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        trailingIcon = {
                            if (isVisible) {
                                IconButton(
                                    onClick = { message = "" }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Clear,
                                        contentDescription = "Clear"
                                    )
                                }
                            }
                        },
                        label = {
                            Text("Сумма")
                        },
                        placeholder = {
                            Text("Введите сумму")
                        },
                        singleLine = true
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.constrainAs(buttonElement) {
                        centerHorizontallyTo(parent)
                        top.linkTo(textFieldElement.bottom, margin = 8.dp)
                    }) {
                    Button(
                        onClick = {
                            text = "$message руб."
                            message = ""
                        }
                    ) {
                        Text("Записать", fontSize = 25.sp)
                    }
                }

            }
        }
    }
}