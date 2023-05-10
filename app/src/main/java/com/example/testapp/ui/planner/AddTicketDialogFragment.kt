package com.example.testapp.ui.planner

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.testapp.databinding.DialogFragmentAddTicketBinding
import com.example.testapp.ui.BaseDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTicketDialogFragment : BaseDialogFragment<DialogFragmentAddTicketBinding>(DialogFragmentAddTicketBinding::inflate) {

    private val viewModel : PlannerViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding.apply {
            btnCancel.setOnClickListener { viewModel.sendTag(TAG) }
            btnApply.setOnClickListener {
                if (etTask.text.isNullOrEmpty()) {
                    Toast.makeText(requireContext(), "Поле не должно быть пустым!", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.addTicket(System.currentTimeMillis(), etTask.text.toString())
                    viewModel.sendTag(TAG)
                }
            }
        }
        return AlertDialog.Builder(requireActivity())
            .setView(binding.root)
            .create()
    }

    companion object {
        const val TAG = "AddTicketDialogFragment"

        fun newInstance(): AddTicketDialogFragment {
            return AddTicketDialogFragment()
        }
    }
}