package com.cartrack.main.ui.splash

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.cartrack.main.R
import com.cartrack.main.databinding.ActivitySplashBinding
import com.cartrack.main.storage.CarTrackKeystore
import com.cartrack.main.ui.base.BaseActivity
import com.cartrack.main.ui.dashboard.DashboardActivity
import com.cartrack.main.ui.login.LoginActivity
import com.cartrack.main.utils.Constants
import dagger.Provides
import javax.inject.Inject

class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    private val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: Int = 1
    private var locationPermissionGranted: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checklocationPermission()
    }

    override fun layoutId(): Int {
        return R.layout.activity_splash
    }

    private fun checkLoginState() {
        Handler(Looper.getMainLooper()).postDelayed({
            var isLoginActive : Boolean = carTrackKeystore.getState()
            if(isLoginActive) {
                var dashboardIntent = Intent(this, DashboardActivity::class.java)
                startActivity(dashboardIntent)
                finish()
            } else {
                var loginIntent = Intent(this, LoginActivity::class.java)
                startActivity(loginIntent)
                finish()
            }

        }, 4000)
    }

    private fun checklocationPermission() {
        if (ContextCompat.checkSelfPermission(this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            checkLoginState()
            return
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        locationPermissionGranted = false
        checkLoginState()
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationPermissionGranted = true
                }
            }
        }
    }
}