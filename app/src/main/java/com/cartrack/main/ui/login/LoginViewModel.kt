package com.cartrack.main.ui.login

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.AndroidViewModel
import com.cartrack.main.data.LoginRepository

import com.cartrack.main.R
import com.cartrack.main.data.db.UserCredentails
import com.cartrack.main.data.LoginDataSource
import com.cartrack.main.data.Result


class LoginViewModel(dataSource: LoginDataSource, application: Application) :
    AndroidViewModel(application) {
    private var repository: LoginRepository = LoginRepository(dataSource, application)

    fun getUser(userName: String, password: String) : LiveData<UserCredentails> {
        return repository.getUser(userName, password)
    }

    private val _loginForm = MutableLiveData<LoginFormState>()

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return username.isNotBlank()
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}