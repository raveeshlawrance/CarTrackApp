package com.cartrack.main.storage

import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.room.Database
import com.cartrack.main.data.db.UserCredentails
import androidx.sqlite.db.SupportSQLiteDatabase

import androidx.annotation.NonNull

import androidx.room.RoomDatabase

import androidx.room.Room
import com.cartrack.main.listener.UserDao
import com.cartrack.main.utils.Constants

@Database(entities = [UserCredentails::class], version = 4, exportSchema = false)
abstract class DatabaseHelper : RoomDatabase() {
    abstract fun userDao(): UserDao?
    companion object {
        private var instance : DatabaseHelper? = null
        @Synchronized
        fun getInstance(context: Context): DatabaseHelper? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    DatabaseHelper::class.java, Constants.DB_NAME_USER_DETAILS)
                    .fallbackToDestructiveMigration()
                    .createFromAsset("databases/user_credentials.db")
                    .addCallback(userDbCallBack)
                    .build()
            }
            return instance
        }

        //userDbCallBack : to handle the db version changes
        private val userDbCallBack: Callback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
            }
        }
    }
}
