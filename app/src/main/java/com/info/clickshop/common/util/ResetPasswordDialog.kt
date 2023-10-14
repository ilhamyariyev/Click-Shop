package com.info.clickshop.common.util

import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.info.clickshop.R
import com.info.clickshop.databinding.ResetPasswordDialogBinding

fun Fragment.setupBottomSheetDialog(
    onSendClick: (String) -> Unit
) {

    val dialog = BottomSheetDialog(requireContext(), R.style.AppBottomSheetDialogTheme)
    val view = layoutInflater.inflate(R.layout.reset_password_dialog, null)
    dialog.setContentView(view)
    dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
    dialog.show()

    val binding = ResetPasswordDialogBinding.bind(view)

    binding.buttonSendResetPassword.setOnClickListener {
        val email = binding.edResetPassword.text.toString().trim()
        onSendClick(email)
        dialog.dismiss()
    }

    binding.buttonCancelResetPassword.setOnClickListener {
        dialog.dismiss()
    }
}