package com.cartrack.main.data

import com.cartrack.main.data.NetworkCallStatus.ERROR
import com.cartrack.main.data.NetworkCallStatus.SUCCESS
import com.cartrack.main.data.NetworkCallStatus.LOADING

data class NetworkResource<out T>(val status: NetworkCallStatus, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T): NetworkResource<T> = NetworkResource(status = SUCCESS, data = data, message = null)

        fun <T> error(data: T?, message: String): NetworkResource<T> =
            NetworkResource(status = ERROR, data = data, message = message)

        fun <T> loading(data: T?): NetworkResource<T> = NetworkResource(status = LOADING, data = data, message = null)
    }
}
