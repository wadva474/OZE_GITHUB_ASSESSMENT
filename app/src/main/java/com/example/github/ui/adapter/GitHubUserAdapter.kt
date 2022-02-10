package com.example.github.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.github.R
import com.example.github.databinding.GithubUserItemListingBinding
import com.example.github.local.model.GithubUser

class GitHubUserAdapter : PagingDataAdapter<GithubUser, GithubUserViewHolder>(GithubUserDiffUtil) {
    override fun onBindViewHolder(holder: GithubUserViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubUserViewHolder {
        return GithubUserViewHolder.from(parent)
    }

}

object GithubUserDiffUtil : DiffUtil.ItemCallback<GithubUser>() {
    override fun areItemsTheSame(oldItem: GithubUser, newItem: GithubUser): Boolean {
        return oldItem.login == newItem.login
    }

    override fun areContentsTheSame(oldItem: GithubUser, newItem: GithubUser): Boolean {
        return oldItem == newItem
    }
}

class GithubUserViewHolder(private val binding: GithubUserItemListingBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: GithubUser?) {
        binding.apply {
            item?.let {
                profileImageView.load(it.avatarUrl) {
                    placeholder(R.drawable.ic_default_profile)
                    error(R.drawable.ic_default_profile)
                    transformations(CircleCropTransformation())
                }
                userNameTextView.text = it.login
                profileLinkTextView.text = it.url
            }
        }
    }

    companion object {
        fun from(parent: ViewGroup): GithubUserViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = GithubUserItemListingBinding.inflate(layoutInflater, parent, false)
            return GithubUserViewHolder(binding)
        }
    }
}