package com.patrick0422.prosleeper.ui

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.core.content.ContextCompat.getSystemService
import com.airbnb.lottie.LottieAnimationView
import com.patrick0422.prosleeper.R
import com.patrick0422.prosleeper.base.BaseFragment
import com.patrick0422.prosleeper.databinding.FragmentHomeBinding
import com.patrick0422.prosleeper.util.Constants.CHANNEL_DESCRIPTION
import com.patrick0422.prosleeper.util.Constants.CHANNEL_ID
import com.patrick0422.prosleeper.util.Constants.CHANNEL_NAME

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val notificationManager: NotificationManager by lazy { getSystemService(requireContext(), NotificationManager::class.java) as NotificationManager }

    override fun init() = with(binding) {
        createNotificationChannel()

        buttonWakeUp.setOnClickListener { onWakeUp() }
        floatingActionButton.setOnClickListener { makeNotification() }
    }

    private fun onWakeUp()  {
        setLottieSrc()
    }

    private fun setLottieSrc() {
        binding.buttonWakeUp.setAnimation(R.raw.check)
        binding.buttonWakeUp.loop(false)
        binding.buttonWakeUp.playAnimation()
    }


    private fun makeNotification() {
        val notification = Notification.Builder(requireContext(), CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_sunny)
            .setContentTitle("일어나셨나요?")
            .setContentText("기상 시각을 기록해주세요!")
            .build()

        notificationManager.notify(1, notification)
    }

    private fun createNotificationChannel() {
        notificationManager.createNotificationChannel(
            NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH).apply { description = CHANNEL_DESCRIPTION }
        )
    }
}