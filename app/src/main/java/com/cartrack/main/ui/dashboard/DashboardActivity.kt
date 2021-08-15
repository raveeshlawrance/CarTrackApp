package com.cartrack.main.ui.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.cartrack.main.R
import com.cartrack.main.databinding.DashboardActivityBinding
import com.cartrack.main.ui.base.BaseActivity
import com.cartrack.main.ui.login.LoginActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.dashboard_activity.*

class DashboardActivity : BaseActivity<DashboardActivityBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navView: BottomNavigationView = nav_view

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_dashboard, R.id.navigation_my_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    fun logout() {
        showAlert(getString(R.string.logout_title), getString(R.string.logout_body_content), object : AlertClickListener {
            override fun onPositiveClickListener() {
                carTrackKeystore.setLoginState(false)
                var loginIntent = Intent(this@DashboardActivity, LoginActivity::class.java)
                startActivity(loginIntent)
                finish()
            }
            override fun onNegativeClickListener() {
            }

        })
    }

    override fun layoutId(): Int {
        return R.layout.dashboard_activity
    }
}