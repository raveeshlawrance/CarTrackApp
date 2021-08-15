package com.cartrack.main.ui.dashboard

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cartrack.main.data.LoginDataSource
import com.cartrack.main.data.LoginRepository
import com.cartrack.main.networkimpl.ApiHelper

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class DashboardViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            return DashboardViewModel(DashboardRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}
