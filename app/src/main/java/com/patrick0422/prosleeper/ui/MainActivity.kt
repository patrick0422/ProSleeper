package com.patrick0422.prosleeper.ui

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.patrick0422.prosleeper.R
import com.patrick0422.prosleeper.base.BaseActivity
import com.patrick0422.prosleeper.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val navHostFragment by lazy { supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment }
    private val navController by lazy { navHostFragment.navController }

    override fun init() {
        setUpBottomNavigationView()
    }

    private fun setUpBottomNavigationView() {
        binding.bottomNav.setupWithNavController(navController)
        setupActionBarWithNavController(navController, AppBarConfiguration(setOf(
            R.id.homeFragment,
            R.id.statisticsFragment,
            R.id.settingsFragment
        )))
    }

    override fun onNavigateUp(): Boolean = navController.navigateUp() || super.onNavigateUp()
}