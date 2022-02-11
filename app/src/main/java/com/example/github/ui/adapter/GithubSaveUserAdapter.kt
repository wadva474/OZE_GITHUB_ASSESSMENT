package com.example.github.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.github.local.model.GithubUser
import com.example.github.ui.adapter.diffutil.GithubUserDiffUtil
import com.example.github.ui.adapter.viewholder.SavedUserViewHolder

class GithubSaveUserAdapter(
    private val deleteFavorite: (GithubUser) -> Unit,
    private val viewMoreDetails: (GithubUser) -> Unit
) : ListAdapter<GithubUser, SavedUserViewHolder>(GithubUserDiffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedUserViewHolder {
        return SavedUserViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SavedUserViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user, deleteFavorite, viewMoreDetails)
    }


}