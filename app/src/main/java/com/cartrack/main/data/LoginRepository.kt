package com.cartrack.main.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.cartrack.main.data.db.UserCredentails

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository(val dataSource: LoginDataSource, application: Application?) {

    fun getUser(username: String, password: String): LiveData<UserCredentails> {
        return dataSource.login(username, password)
    }
}