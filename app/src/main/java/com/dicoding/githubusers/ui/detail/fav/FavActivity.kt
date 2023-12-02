package com.dicoding.githubusers.ui.detail.fav

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubusers.data.Response.Users
import com.dicoding.githubusers.data.favorite.FavsignUsers
import com.dicoding.githubusers.databinding.ActivityFavBinding
import com.dicoding.githubusers.ui.UsersAdapter
import com.dicoding.githubusers.ui.detail.UsersDetailActivity

class FavActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavBinding
    private lateinit var adapter: UsersAdapter
    private lateinit var viewModel: FavViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UsersAdapter()
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this).get(FavViewModel::class.java)

        adapter.setOnItemClickCallback(object : UsersAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Users) {
                Intent(this@FavActivity, UsersDetailActivity::class.java).also {
                    it.putExtra(UsersDetailActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(UsersDetailActivity.EXTRA_ID, data.id)
                    it.putExtra(UsersDetailActivity.EXTRA_URL, data.avatar_url)
                    startActivity(it)
                }
            }

        })

        binding.apply {
            rvUsers.setHasFixedSize(true)
            rvUsers.layoutManager = LinearLayoutManager(this@FavActivity)
            rvUsers.adapter = adapter
        }
        viewModel.getFavUsers()?.observe(this, {
            if (it != null) {
                val list = favList(it)
                adapter.setList(list as ArrayList<Users>)
            }
        })
    }

    private fun favList(users: List<FavsignUsers>): Any {
        val listUsers = ArrayList<Users>()
        for (users in users) {
            val usersMapped = Users(
                users.login,
                users.id,
                users.avatar_url
            )
            listUsers.add(usersMapped)
        }
        return listUsers
    }
}