package com.patrick0422.prosleeper.ui

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.patrick0422.prosleeper.R
import com.patrick0422.prosleeper.base.BaseActivity
import com.patrick0422.prosleeper.databinding.ActivityMainBinding
import com.patrick0422.prosleeper.util.AlarmReceiver
import com.patrick0422.prosleeper.util.Constants.ALARM_REQUEST_CODE
import com.patrick0422.prosleeper.util.Constants.TAG
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.util.*

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val mainViewModel: MainViewModel by viewModels()
    private val navHostFragment by lazy { supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment }
    private val navController by lazy { navHostFragment.navController }

    override fun init() {
        setUpBottomNavigationView()

        mainViewModel.isNotificationAllowed.asLiveData().observe(this) { setAlarm(it) }

        supportActionBar?.hide()
    }

    private fun setAlarm(isEnabled: Boolean) {
        Log.d(TAG, "setAlarm: Called, isEnabled = $isEnabled")
        cancelAlarm()
        if (!isEnabled) {
            return
        }

        val alarmManager = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        mainViewModel.notificationTime.asLiveData().observe(this) {
            val time = it.split(':')

            val calendar = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, time[0].toInt())
                set(Calendar.MINUTE, time[1].toInt())
            }
            Log.d(TAG, "setAlarm: ${calendar}")

            val pendingIntent = PendingIntent.getBroadcast(
                this,
                ALARM_REQUEST_CODE,
                Intent(this, AlarmReceiver::class.java),
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )
        }
    }

    private fun cancelAlarm() = PendingIntent.getBroadcast(
        this,
        ALARM_REQUEST_CODE,
        Intent(this, AlarmReceiver::class.java),
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )?.let {
        Log.d(TAG, "cancelAlarm: Called")
        (applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager).cancel(it)
    }


    private fun setUpBottomNavigationView() {
        binding.bottomNav.setupWithNavController(navController)
        setupActionBarWithNavController(
            navController, AppBarConfiguration(
                setOf(
                    R.id.homeFragment,
                    R.id.statisticsFragment,
                    R.id.settingsFragment
                )
            )
        )
    }

    override fun onNavigateUp(): Boolean = navController.navigateUp() || super.onNavigateUp()
}