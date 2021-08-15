package com.cartrack.main.networkimpl

import com.cartrack.main.data.dashboard.UserListResponseItem
import retrofit2.http.GET

interface APIServices {
    @GET("users")
    suspend fun getUsers() : List<UserListResponseItem>
}