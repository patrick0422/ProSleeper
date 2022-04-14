package com.patrick0422.prosleeper.ui

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.patrick0422.prosleeper.R
import com.patrick0422.prosleeper.base.BaseActivity
import com.patrick0422.prosleeper.databinding.ActivityMainBinding
import com.patrick0422.prosleeper.util.AlarmReceiver
import com.patrick0422.prosleeper.util.Constants.ALARM_REQUEST_CODE
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalTime
import java.util.*

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val mainViewModel: MainViewModel by viewModels()
    private val navHostFragment by lazy { supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment }
    private val navController by lazy { navHostFragment.navController }

    override fun init() {
        setUpBottomNavigationView()
        mainViewModel.isNotificationAllowed.observe(this) { setAlarm(it) }
        supportActionBar?.hide()
    }

    private fun setAlarm(isEnabled: Boolean) {
        if (!isEnabled) {
            cancelAlarm()
            return
        }
        makeToast("Called")

        val alarmManager =
            applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val time = (mainViewModel.notificationTime.value?.split(':') ?: listOf("7", "0")).let {
            return@let LocalTime.of(it[0].toInt(), it[1].toInt())
        }

        val timeInMills = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, time.hour)
            set(Calendar.MINUTE, time.minute)

            if (before(Calendar.getInstance())) {
                add(Calendar.DATE, 1)
            }
        }.timeInMillis

        val intent = Intent(this, AlarmManager::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            ALARM_REQUEST_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            timeInMills,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

    private fun cancelAlarm() = PendingIntent.getBroadcast(
        this,
        ALARM_REQUEST_CODE,
        Intent(this, AlarmReceiver::class.java),
        PendingIntent.FLAG_NO_CREATE
    )?.cancel()


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