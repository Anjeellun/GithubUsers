package com.dicoding.githubusers.data.favorite

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DataAccessObjectFavUsers {

    @Insert
    suspend fun addToFav(favsignUsers: FavsignUsers)

    @Query("SELECT * FROM favsign_users")
    fun getFavUsers(): LiveData<List<FavsignUsers>>

    @Query("SELECT count(*) FROM favsign_users WHERE favsign_users.id = :id")
    suspend fun checkUsers(id: Int): Int

    @Query("DELETE FROM favsign_users WHERE favsign_users.id = :id")
    suspend fun removeFromFav(id: Int): Int
}