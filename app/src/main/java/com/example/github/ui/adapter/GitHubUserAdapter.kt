package com.example.github.ui.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.example.github.local.model.GithubUser
import com.example.github.ui.adapter.diffutil.GithubUserDiffUtil
import com.example.github.ui.adapter.viewholder.GithubUserViewHolder

class GitHubUserAdapter(
    private val addToFavorite: (GithubUser) -> Unit,
    private val viewMoreDetails: (GithubUser) -> Unit
) : PagingDataAdapter<GithubUser, GithubUserViewHolder>(GithubUserDiffUtil) {
    override fun onBindViewHolder(holder: GithubUserViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user, addToFavorite,viewMoreDetails)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubUserViewHolder {
        return GithubUserViewHolder.from(parent)
    }
}