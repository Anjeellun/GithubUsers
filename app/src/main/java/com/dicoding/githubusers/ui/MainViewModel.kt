package com.dicoding.githubusers.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubusers.api.RetrofitClients
import com.dicoding.githubusers.data.Response.Users
import com.dicoding.githubusers.data.Response.UsersResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    val listUsers = MutableLiveData<ArrayList<Users>>()

    fun setSearchUsers(query: String) {
        RetrofitClients.apiInstance
            .getSearchUsers(query)
            .enqueue(object : Callback<UsersResponse> {
                override fun onResponse(
                    call: Call<UsersResponse>,
                    response: Response<UsersResponse>
                ) {
                    if (response.isSuccessful) {
                        listUsers.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                    t.message?.let { Log.d("Failure", it) }

                }

            })
    }

    fun getDefaultUsers() {
        val query = "popular"
        RetrofitClients.apiInstance
            .getSearchUsers(query)
            .enqueue(object : Callback<UsersResponse> {
                override fun onResponse(
                    call: Call<UsersResponse>,
                    response: Response<UsersResponse>
                ) {
                    if (response.isSuccessful) {
                        listUsers.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                    t.message?.let { Log.d("Failure", it) }
                }
            })
    }


    fun getSearchUsers(): LiveData<ArrayList<Users>> {
        return listUsers
    }
}