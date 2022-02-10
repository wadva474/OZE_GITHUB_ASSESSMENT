package com.example.github.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.github.databinding.ItemGithubUserFooterStateBinding

class GitHubUserFooterStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<GitHubUserFooterStateAdapter.GithubUserFooterViewHolder>() {

    override fun onBindViewHolder(holder: GithubUserFooterViewHolder, loadState: LoadState) {
        holder.bind(loadState, retry)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): GithubUserFooterViewHolder {
        return GithubUserFooterViewHolder.create(parent)
    }

    class GithubUserFooterViewHolder(
        private val binding: ItemGithubUserFooterStateBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(loadState: LoadState, retry: () -> Unit) {
            binding.retryBtn.setOnClickListener {
                retry.invoke()
            }
            if (loadState is LoadState.Error) {
                binding.errorMessage.text = loadState.error.message
            }
            binding.progressIndicator.isVisible = loadState is LoadState.Loading
            binding.retryBtn.isVisible = loadState is LoadState.Error
            binding.errorMessage.isVisible = loadState !is LoadState.Loading

        }
        companion object {
            fun create(parent: ViewGroup): GithubUserFooterViewHolder {
                val binding = ItemGithubUserFooterStateBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return GithubUserFooterViewHolder(binding)
            }
        }
    }
}