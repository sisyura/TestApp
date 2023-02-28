package com.example.testapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.firebase.auth.FirebaseAuth

open class BaseFragment<VB : ViewBinding>(private val bindingInflater: (layoutInflater: LayoutInflater) -> VB) : Fragment() {

    private var _auth: FirebaseAuth? = null
    val auth get() = requireNotNull(_auth)
    private var _binding: VB? = null
    val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _auth = FirebaseAuth.getInstance()
        _binding = bindingInflater.invoke(inflater)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _auth = null
        _binding = null
    }
}