package com.example.testapp.ui.dashboard

import android.os.Bundle
import android.text.TextUtils.replace
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.testapp.R
import com.example.testapp.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding

    private val viewModel: DashboardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_dashboard, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDashboardBinding.bind(view)
        binding.apply {
            viewModel.text.observe(viewLifecycleOwner) {
                textDashboard.text = it
            }
            var sum = "${etSum.text.toString().replace(",", " ")} руб."
            btnApply.setOnClickListener { textDashboard.text = "${etSum.text.toString().replace(",", " ")} руб." }
        }
    }
}