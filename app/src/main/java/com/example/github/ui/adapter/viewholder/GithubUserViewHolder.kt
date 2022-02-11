package com.example.github.ui.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.github.R
import com.example.github.databinding.GithubUserItemListingBinding
import com.example.github.local.model.GithubUser

class GithubUserViewHolder(private val binding: GithubUserItemListingBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: GithubUser?,
        addToFavorite: (GithubUser) -> Unit,
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
                imageView.setColorFilter(if (item.isFavourite) ContextCompat.getColor(root.context, R.color.red) else ContextCompat.getColor(root.context, R.color.grey))
                imageView.setOnClickListener {
                    item.isFavourite = !item.isFavourite
                    imageView.setColorFilter(if (item.isFavourite) ContextCompat.getColor(root.context, R.color.red) else ContextCompat.getColor(root.context, R.color.grey))
                    if (item.isFavourite) addToFavorite.invoke(item)
                }
                root.setOnClickListener {
                    viewMoreDetails.invoke(item)
                }
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