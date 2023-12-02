package com.dicoding.githubusers.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubusers.R
import com.dicoding.githubusers.data.Response.Users
import com.dicoding.githubusers.databinding.ActivityMainBinding
import com.dicoding.githubusers.ui.detail.UsersDetailActivity
import com.dicoding.githubusers.ui.detail.fav.FavActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: UsersAdapter
    private lateinit var clickMeButton: ImageButton
    private lateinit var themeToggleButton: ToggleButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = getSharedPreferences("ThemeMode", Context.MODE_PRIVATE)
        val isDarkModeOn = sharedPreferences.getBoolean("DarkModeOn", false)

        if (isDarkModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UsersAdapter()
        adapter.notifyDataSetChanged()
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getDefaultUsers()

        clickMeButton = findViewById(R.id.ClickMe)
        themeToggleButton = findViewById(R.id.theme_btn)

        adapter.setOnItemClickCallback(object : UsersAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Users) {
                Intent(this@MainActivity, UsersDetailActivity::class.java).also {
                    it.putExtra(UsersDetailActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(UsersDetailActivity.EXTRA_ID, data.id)
                    it.putExtra(UsersDetailActivity.EXTRA_URL, data.avatar_url)
                    startActivity(it)
                }
            }
        })

        binding.apply {
            recyclerviewUser.layoutManager = LinearLayoutManager(this@MainActivity)
            recyclerviewUser.setHasFixedSize(true)
            recyclerviewUser.adapter = adapter

            binding.btnSearch.setOnClickListener {
                searchUsers()
            }

            editTextQuery.setOnKeyListener { _, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    searchUsers()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }

            clickMeButton.setOnClickListener {
                onClickButtonClicked()
            }

            themeToggleButton.setOnClickListener {
                val editor = sharedPreferences.edit()
                if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    editor.putBoolean("DarkModeOn", false)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    editor.putBoolean("DarkModeOn", true)
                }

                editor.apply()
                recreate()
            }
        }

        viewModel.getSearchUsers().observe(this, {
            if (it != null) {
                adapter.setList(it)
                showLoading(false)
            }
        })

        viewModel.getSearchUsers().observe(this, {
            if (it != null) {
                adapter.setList(it)
                showLoading(false)
            }
        })
    }

    private fun searchUsers() {
        binding.apply {
            val query = editTextQuery.text.toString()
            if (query.isEmpty()) return
            showLoading(true)
            viewModel.setSearchUsers(query)
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressbar.visibility = View.VISIBLE
        } else {
            binding.progressbar.visibility = View.GONE
        }
    }

    private fun onClickButtonClicked() {
        Toast.makeText(this, "Showing Your Favorite Users!", Toast.LENGTH_SHORT).show()

        val intent = Intent(this@MainActivity, FavActivity::class.java)
        startActivity(intent)
    }
}
