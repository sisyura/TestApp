package com.example.testapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.google.firebase.auth.FirebaseAuth

open class BaseDialogFragment<VB : ViewBinding>(private val bindingInflater: (layoutInflater: LayoutInflater) -> VB) : DialogFragment() {

    private var _auth: FirebaseAuth? = null
    val auth get() = requireNotNull(_auth)
    private var _binding: VB? = null
    val binding get() = requireNotNull(_binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingInflater.invoke(LayoutInflater.from(context))
        _auth = FirebaseAuth.getInstance()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _auth = null
        _binding = null
    }
}