package com.patrick0422.prosleeper.ui.settings

import android.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import com.patrick0422.prosleeper.R
import com.patrick0422.prosleeper.base.BaseFragment
import com.patrick0422.prosleeper.databinding.DialogTimePickerBinding
import com.patrick0422.prosleeper.databinding.FragmentSettingsBinding
import com.patrick0422.prosleeper.ui.MainViewModel
import com.patrick0422.prosleeper.util.Constants.TAG
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import java.time.LocalTime

@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingsBinding>(R.layout.fragment_settings) {
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun init() = with(binding) {
        textNotificationTime.setOnClickListener {
            showTimePickerDialog()
        }
        mainViewModel.notificationTime.asLiveData().observe(viewLifecycleOwner) { notificationTime ->
            textNotificationTime.text = notificationTime
        }
        mainViewModel.isNotificationAllowed.asLiveData().observe(viewLifecycleOwner) { isAllowed ->
            switchAllowNotification.isChecked = isAllowed
        }
        switchAllowNotification.setOnCheckedChangeListener { _, isChecked ->
            Log.d(TAG, "init: isChecked = $isChecked")
            mainViewModel.saveIsNotificationAllowed(isChecked)
        }
    }

    private fun showTimePickerDialog() {
        val dialogTimePickerBinding: DialogTimePickerBinding = DataBindingUtil.inflate(
            LayoutInflater.from(requireContext()), R.layout.dialog_time_picker, null, false
        )

        val alertDialog = AlertDialog.Builder(requireContext(), R.style.TimePickerDialog)
            .setView(dialogTimePickerBinding.root)
            .create()

        with(dialogTimePickerBinding) {
            mainViewModel.notificationTime.asLiveData().observe(viewLifecycleOwner) {
                val savedTime = it.split(':')
                timePicker.hour = savedTime[0].toInt()
                timePicker.minute = savedTime[1].toInt()
            }

            textApply.setOnClickListener {
                saveNotificationTime(timePicker.hour, timePicker.minute)
                alertDialog.dismiss()
            }
            textCancel.setOnClickListener { alertDialog.dismiss() }
        }

        alertDialog.show()
    }

    private fun saveNotificationTime(hour: Int, minute: Int) {
        val notificationTime = LocalTime.of(hour, minute).toString()
        binding.textNotificationTime.text = notificationTime
        mainViewModel.saveNotificationTime(notificationTime)
    }
}