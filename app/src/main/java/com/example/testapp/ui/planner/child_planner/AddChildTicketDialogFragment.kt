package com.example.testapp.ui.planner.child_planner

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.testapp.databinding.DialogFragmentAddTicketBinding
import com.example.testapp.ui.BaseDialogFragment
import com.example.testapp.ui.planner.AddTicketDialogFragment
import com.example.testapp.ui.planner.PlannerViewModel

class AddChildTicketDialogFragment(private val parentId: Int) : BaseDialogFragment<DialogFragmentAddTicketBinding>(DialogFragmentAddTicketBinding::inflate){

    private val viewModel : PlannerViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding.apply {
            tvNewTask.text = "Новая подзадача"
            btnCancel.setOnClickListener { viewModel.sendTag(TAG) }
            btnApply.setOnClickListener {
                if (etTask.text.isNullOrEmpty()) {
                    Toast.makeText(requireContext(), "Поле не должно быть пустым!", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.addChildTicket(System.currentTimeMillis(), etTask.text.toString(), parentId)
                    viewModel.sendTag(TAG)
                }
            }
        }
        return AlertDialog.Builder(requireActivity())
            .setView(binding.root)
            .create()
    }

    companion object {
        const val TAG = "AddChildTicketDialogFragment"

        fun newInstance(parentId: Int): AddChildTicketDialogFragment {
            return AddChildTicketDialogFragment(parentId)
        }
    }

}