package com.example.github.ui.adapter.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.example.github.local.model.GithubUser

object GithubUserDiffUtil : DiffUtil.ItemCallback<GithubUser>() {
    override fun areItemsTheSame(oldItem: GithubUser, newItem: GithubUser): Boolean {
        return oldItem.login == newItem.login
    }

    override fun areContentsTheSame(oldItem: GithubUser, newItem: GithubUser): Boolean {
        return oldItem == newItem
    }
}