package com.cartrack.main.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.cartrack.main.data.NetworkResource
import kotlinx.coroutines.Dispatchers

class DashboardViewModel(private val mainRepository: DashboardRepository) : ViewModel() {

    fun getUsers() = liveData(Dispatchers.IO) {
        emit(NetworkResource.loading(data = null))
        try {
            emit(NetworkResource.success(data = mainRepository.getUsers()))
        } catch (exception: Exception) {
            emit(NetworkResource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}