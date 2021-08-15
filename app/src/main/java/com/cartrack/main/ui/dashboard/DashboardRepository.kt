package com.cartrack.main.ui.dashboard

import com.cartrack.main.networkimpl.ApiHelper

class DashboardRepository(private val apiHelper: ApiHelper) {
    suspend fun getUsers() = apiHelper.getUsers()
}
