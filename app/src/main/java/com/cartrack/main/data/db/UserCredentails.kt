package com.cartrack.main.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cartrack.main.utils.Constants

@Entity(tableName = Constants.TABLE_NAME_USER_DETAILS)
data class UserCredentails(var username : String,
                           var password : String,
                           @PrimaryKey
                           var user_id : Int
)

