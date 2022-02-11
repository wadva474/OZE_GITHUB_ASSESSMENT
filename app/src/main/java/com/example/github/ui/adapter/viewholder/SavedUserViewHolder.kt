package com.example.github.ui.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.github.R
import com.example.github.databinding.ItemSavedGithubUserBinding
import com.example.github.local.model.GithubUser

class SavedUserViewHolder (private val binding: ItemSavedGithubUserBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: GithubUser?,
        deleteFavorite: (GithubUser) -> Unit,
        viewMoreDetails: (GithubUser) -> Unit
    ) {
        binding.apply {
            item?.let {
                profileImageView.load(it.avatarUrl) {
                    placeholder(R.drawable.ic_default_profile)
                    error(R.drawable.ic_default_profile)
                    transformations(CircleCropTransformation())
                }
                userNameTextView.text = it.login
                profileLinkTextView.text = it.url
                imageView.setOnClickListener {
                    deleteFavorite.invoke(item)
                }
                root.setOnClickListener {
                    viewMoreDetails.invoke(item)
                }
            }
        }
    }

    companion object {
        fun from(parent: ViewGroup): SavedUserViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemSavedGithubUserBinding.inflate(layoutInflater, parent, false)
            return SavedUserViewHolder(binding)
        }
    }
}