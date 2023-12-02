package com.dicoding.githubusers.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.githubusers.api.RetrofitClients
import com.dicoding.githubusers.data.Response.UsersDetailResponse
import com.dicoding.githubusers.data.favorite.DataAccessObjectFavUsers
import com.dicoding.githubusers.data.favorite.FavsignUsers
import com.dicoding.githubusers.data.favorite.UsersDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsersDetailViewModel(application: Application) : AndroidViewModel(application) {
    val users = MutableLiveData<UsersDetailResponse>()

    private val DaoUsers: DataAccessObjectFavUsers?
    private val UsersDb: UsersDatabase?

    init {
        UsersDb = UsersDatabase.getDatabase(application)
        DaoUsers = UsersDb?.DataAccessObjectFavUsers()
    }

    fun setUsersDetail(username: String) {
        RetrofitClients.apiInstance
            .getUsersDetail(username)
            .enqueue(object : Callback<UsersDetailResponse> {
                override fun onResponse(
                    call: Call<UsersDetailResponse>,
                    response: Response<UsersDetailResponse>
                ) {
                    if (response.isSuccessful) {
                        users.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<UsersDetailResponse>, t: Throwable) {
                    t.message?.let { Log.d("Failure", it) }
                }

            })
    }

    fun getUsersDetail(): LiveData<UsersDetailResponse> {
        return users
    }

    fun addToFav(username: String, id: Int, avatarUrl: String) {
        CoroutineScope(Dispatchers.IO).launch {
            var users = FavsignUsers(
                username,
                id,
                avatarUrl
            )
            DaoUsers?.addToFav(users)
        }
    }

    suspend fun checkUsers(id: Int) = DaoUsers?.checkUsers(id)

    fun removeFromFav(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            DaoUsers?.removeFromFav(id)
        }
    }

}