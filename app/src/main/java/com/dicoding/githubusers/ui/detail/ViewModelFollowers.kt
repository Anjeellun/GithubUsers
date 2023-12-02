package com.dicoding.githubusers.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubusers.api.RetrofitClients
import com.dicoding.githubusers.data.Response.Users
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModelFollowers : ViewModel() {
    val listFollowers = MutableLiveData<ArrayList<Users>>()

    fun setListFollowers(username: String) {
        RetrofitClients.apiInstance
            .getFollowers(username)
            .enqueue(object : Callback<ArrayList<Users>> {
                override fun onResponse(
                    call: Call<ArrayList<Users>>,
                    response: Response<ArrayList<Users>>
                ) {
                    if (response.isSuccessful) {
                        listFollowers.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<Users>>, t: Throwable) {
                    t.message?.let { Log.d("Failure", it) }
                }

            })
    }

    fun getListFollowers(): LiveData<ArrayList<Users>> {
        return listFollowers
    }
}