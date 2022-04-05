package com.patrick0422.prosleeper.ui.home

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import com.google.android.material.snackbar.Snackbar
import com.patrick0422.prosleeper.R
import com.patrick0422.prosleeper.base.BaseFragment
import com.patrick0422.prosleeper.data.local.WakeUpTimeEntity
import com.patrick0422.prosleeper.databinding.FragmentHomeBinding
import com.patrick0422.prosleeper.ui.MainViewModel
import com.patrick0422.prosleeper.util.Constants.CHANNEL_DESCRIPTION
import com.patrick0422.prosleeper.util.Constants.CHANNEL_ID
import com.patrick0422.prosleeper.util.Constants.CHANNEL_NAME
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val notificationManager: NotificationManager by lazy { getSystemService(requireContext(), NotificationManager::class.java) as NotificationManager }
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun init() = with(binding) {
        createNotificationChannel()



        buttonWakeUp.setOnClickListener { onWakeUp() }
        floatingActionButton.setOnClickListener { makeNotification() }
    }

    private fun onWakeUp()  {
        if (mainViewModel.isTodaysWakeUpTimeSaved) {
            makeSnackbar(R.string.on_already_saved_message)
        } else {
            applyLottieStyle()
            saveWakeUpTime()
            makeSnackbar(R.string.on_wakeup_time_saved_message)
        }
    }

    private fun saveWakeUpTime() {
        val wakeUpTimeEntity = WakeUpTimeEntity(LocalDateTime.now())
        mainViewModel.insertWakeUpTime(wakeUpTimeEntity)
    }

    private fun applyLottieStyle() {
        binding.buttonWakeUp.setAnimation(R.raw.check)
        binding.buttonWakeUp.loop(false)
        binding.buttonWakeUp.playAnimation()
    }

    fun checkIsTodaysWakeUpTimeSaved() {
        mainViewModel.getWakeUpTimes().asLiveData().observe(viewLifecycleOwner) {

        }
    }

    private fun makeSnackbar(id: Int) {
        Snackbar.make(binding.root, getString(id), Snackbar.LENGTH_LONG)
            .setAction("옙") {}
            .show()
    }

    private fun makeNotification() {
        val notification = Notification.Builder(requireContext(), CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_sunny)
            .setContentTitle(getString(R.string.notification_title))
            .setContentText(getString(R.string.notification_content))
            .build()

        notificationManager.notify(1, notification)
    }

    private fun createNotificationChannel() {
        notificationManager.createNotificationChannel(
            NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH).apply { description = CHANNEL_DESCRIPTION }
        )
    }
}