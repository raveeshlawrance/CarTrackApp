package com.cartrack.main.data

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.cartrack.main.data.db.UserCredentails
import com.cartrack.main.listener.UserDao
import com.cartrack.main.storage.DatabaseHelper
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource(application: Application) {
    val database: DatabaseHelper? = DatabaseHelper.getInstance(application?.applicationContext!!)
    var userDao: UserDao = database?.userDao()!!

    fun login(username: String, password: String) : LiveData<UserCredentails> {
        return userDao.getUser(username, password)
    }
}