package com.dicoding.githubusers.ui.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dicoding.githubusers.databinding.ActivityUsersDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UsersDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_URL = "extra_url"

    }

    private lateinit var binding: ActivityUsersDetailBinding
    private lateinit var viewModel: UsersDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsersDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatarUrl = intent.getStringExtra(EXTRA_URL) ?: ""
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        showLoading(true)

        if (username != null) {
            viewModel = ViewModelProvider(this).get(UsersDetailViewModel::class.java)

            viewModel.setUsersDetail(username)
            viewModel.getUsersDetail().observe(this, {
                if (it != null) {
                    binding.apply {
                        namedetail.text = it.name
                        usernamedetail.text = it.login
                        followersdetail.text = "${it.followers} Followers"
                        followingdetail.text = "${it.following} Following"

                        Glide.with(this@UsersDetailActivity)
                            .load(it.avatar_url)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .centerCrop()
                            .into(imageviewDetail)
                    }
                    showLoading(false)
                } else {
                    showLoading(true)
                }
            })

            var isChecked = false
            CoroutineScope(Dispatchers.IO).launch {
                val count = viewModel.checkUsers(id)
                withContext(Dispatchers.Main) {
                    if (count != null) {
                        if (count > 0) {
                            binding.favoriteSign.isChecked = true
                            isChecked = true
                        } else {
                            binding.favoriteSign.isChecked = false
                            isChecked = false
                        }
                    }
                }
            }

            binding.favoriteSign.setOnClickListener {
                isChecked = !isChecked
                if (isChecked) {
                    viewModel.addToFav(username, id, avatarUrl)
                } else {
                    viewModel.removeFromFav(id)
                }
                binding.favoriteSign.isChecked = isChecked
            }

            val PagerAdapter = PagerAdapter(this, supportFragmentManager, bundle)
            binding.apply {
                viewpagerdetail.adapter = PagerAdapter
                tab.setupWithViewPager(viewpagerdetail)
            }

        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressbar.visibility = View.VISIBLE
        } else {
            binding.progressbar.visibility = View.GONE
        }
    }
}

