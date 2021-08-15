package com.cartrack.main.networkimpl

class ApiHelper(private val apiService: APIServices) {
    suspend fun getUsers() = apiService.getUsers()
}
