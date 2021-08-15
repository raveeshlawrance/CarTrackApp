package com.cartrack.main.listener

import androidx.lifecycle.LiveData
import androidx.room.*
import com.cartrack.main.data.Result
import com.cartrack.main.data.db.UserCredentails

@Dao
interface UserDao {
    @Insert
    fun Insert(note: UserCredentails?)

    @Query("SELECT * FROM user_details")
    fun getAllUsers(): Array<UserCredentails>

    @Query("SELECT * FROM user_details WHERE username = :username AND password = :password")
    fun getUser(username: String?, password: String?): LiveData<UserCredentails>
}
