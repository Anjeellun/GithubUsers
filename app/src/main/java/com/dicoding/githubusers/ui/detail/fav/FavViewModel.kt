package com.dicoding.githubusers.ui.detail.fav

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.dicoding.githubusers.data.favorite.DataAccessObjectFavUsers
import com.dicoding.githubusers.data.favorite.FavsignUsers
import com.dicoding.githubusers.data.favorite.UsersDatabase

class FavViewModel(application: Application) : AndroidViewModel(application) {

    private val DaoUsers: DataAccessObjectFavUsers?
    private val UsersDb: UsersDatabase?

    init {
        UsersDb = UsersDatabase.getDatabase(application)
        DaoUsers = UsersDb?.DataAccessObjectFavUsers()
    }

    fun getFavUsers(): LiveData<List<FavsignUsers>>? {
        return DaoUsers?.getFavUsers()
    }
}