package com.cartrack.main.ui.login

import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.annotation.StringRes
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.AppCompatEditText

import com.cartrack.main.R
import com.cartrack.main.databinding.ActivityLoginBinding
import com.cartrack.main.ui.base.BaseActivity
import com.cartrack.main.ui.dashboard.DashboardActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.dashboard_fragment.*


class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val loading = findViewById<ProgressBar>(R.id.loading)

        loginViewModel = ViewModelProviders.of(this, LoginViewModelFactory(application))
            .get(LoginViewModel::class.java)

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (loginResult.error != null) {
            }
            if (loginResult.success != null) {
            }
        })

        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
            )
        }

        checkbox_stay_loggedin.setOnCheckedChangeListener {checkBoxView, isChecked ->
            carTrackKeystore.setLoginState(isChecked)
        }
        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                        username.text.toString(),
                        password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        onCallLoginService()
                }
                false
            }

            login.setOnClickListener {
                onCallLoginService()
            }
        }
    }

    private fun onCallLoginService() {
        if(username.text.toString().trim() == "" || password.text.toString().trim() == "") {
            showAlert(getString(R.string.login_error_title), getString(R.string.login_missing_body_content))
            return
        }
        loading.visibility = View.VISIBLE
        loginViewModel.getUser(username.text.toString(), password.text.toString()).observe(this@LoginActivity, Observer {
            val loginResult = it
            loading.visibility = View.GONE
            if (loginResult != null) {
                updateUiWithUser()
            } else {
                showAlert(getString(R.string.login_error_title), getString(R.string.login_incorrect_credentials))
            }
        })
    }

    override fun layoutId(): Int {
        return R.layout.activity_login
    }

    private fun updateUiWithUser() {
        var dashboardIntent = Intent(this, DashboardActivity::class.java)
        startActivity(dashboardIntent)
        finish()
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}