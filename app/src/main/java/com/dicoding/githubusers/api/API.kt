package com.dicoding.githubusers.api

import com.dicoding.githubusers.data.Response.Users
import com.dicoding.githubusers.data.Response.UsersDetailResponse
import com.dicoding.githubusers.data.Response.UsersResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface API {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearchUsers(
        @Query("q") query: String
    ): Call<UsersResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUsersDetail(
        @Path("username") username: String
    ): Call<UsersDetailResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<Users>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<Users>>
}