package com.dicoding.githubusers.data.favorite

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favsign_users")
data class FavsignUsers(
    val login: String,
    @PrimaryKey
    val id: Int,
    val avatar_url: String
) : Serializable