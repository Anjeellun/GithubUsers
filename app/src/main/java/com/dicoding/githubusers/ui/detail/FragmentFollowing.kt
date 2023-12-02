package com.dicoding.githubusers.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubusers.R
import com.dicoding.githubusers.databinding.FragmentFollowingFollowersBinding
import com.dicoding.githubusers.ui.UsersAdapter

class FragmentFollowing: Fragment(R.layout.fragment_following_followers) {

    private var _binding: FragmentFollowingFollowersBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModelFollowing: ViewModelFollowing
    private lateinit var adapter: UsersAdapter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        username = args?.getString(UsersDetailActivity.EXTRA_USERNAME).toString()

        _binding = FragmentFollowingFollowersBinding.bind(view)

        adapter = UsersAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            recyclerviewUser.setHasFixedSize(true)
            recyclerviewUser.layoutManager = LinearLayoutManager(activity)
            recyclerviewUser.adapter = adapter
        }

        showLoading(true)
        viewModelFollowing = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(ViewModelFollowing::class.java)
        viewModelFollowing.setListFollowing(username)
        viewModelFollowing.getListFollowing().observe(viewLifecycleOwner, {
            if (it != null) {
                adapter.setList(it)
                showLoading(false)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressbar.visibility = View.VISIBLE
        } else {
            binding.progressbar.visibility = View.GONE
        }
    }
}