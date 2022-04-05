package com.patrick0422.prosleeper.ui.settings

import android.app.AlertDialog
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.patrick0422.prosleeper.R
import com.patrick0422.prosleeper.base.BaseFragment
import com.patrick0422.prosleeper.databinding.DialogTimePickerBinding
import com.patrick0422.prosleeper.databinding.FragmentSettingsBinding

class SettingsFragment : BaseFragment<FragmentSettingsBinding>(R.layout.fragment_settings) {


    override fun init() = with(binding) {
        textNotificationTime.setOnClickListener {
            showTimePickerDialog()
        }
    }

    private fun showTimePickerDialog() {
        val dialogTimePickerBinding: DialogTimePickerBinding = DataBindingUtil.inflate(
            LayoutInflater.from(requireContext()), R.layout.dialog_time_picker, null, false
        )

        val alertDialog = AlertDialog.Builder(requireContext(), R.style.TimePickerDialog)
            .setView(dialogTimePickerBinding.root)
            .create()

        dialogTimePickerBinding.textApply.setOnClickListener { alertDialog.dismiss() }
        dialogTimePickerBinding.textCancel.setOnClickListener { alertDialog.dismiss() }

        alertDialog.show()
    }
}