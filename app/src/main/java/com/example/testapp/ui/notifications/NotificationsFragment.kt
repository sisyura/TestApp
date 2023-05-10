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
}