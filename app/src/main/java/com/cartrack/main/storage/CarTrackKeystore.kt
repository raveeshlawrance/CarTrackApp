package com.cartrack.main.storage

import android.content.Context
import android.content.SharedPreferences
import com.cartrack.main.utils.Constants

class CarTrackKeystore(appContext: Context) {
    private val preferences : SharedPreferences = appContext.getSharedPreferences(
        Constants.LOGIN_STATE_FILE,
        Context.MODE_PRIVATE
    )

    fun setLoginState(_loginState: Boolean) {
        preferences.edit().putBoolean(Constants.LOGIN_STATUS, _loginState).apply()
    }

    fun getState() : Boolean {
        return preferences.getBoolean(Constants.LOGIN_STATUS, false)
    }
}