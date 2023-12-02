package com.dicoding.githubusers.data.favorite

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [FavsignUsers::class],
    version = 2
)

abstract class UsersDatabase : RoomDatabase() {

    companion object {
        @Volatile
        var INSTANCE: UsersDatabase? = null

        fun getDatabase(context: Context): UsersDatabase? {
            if (INSTANCE == null) {
                synchronized(UsersDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        UsersDatabase::class.java,
                        "database_users"
                    ).fallbackToDestructiveMigration()
                        .build()
                }
            }

            return INSTANCE

        }
    }

    abstract fun DataAccessObjectFavUsers(): DataAccessObjectFavUsers

}