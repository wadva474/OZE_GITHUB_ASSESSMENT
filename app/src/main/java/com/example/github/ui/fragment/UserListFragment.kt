package com.example.github.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import com.example.github.base.BaseFragment
import com.example.github.databinding.FragmentGithubUsersBinding
import com.example.github.ui.adapter.GitHubUserAdapter
import com.example.github.ui.adapter.GitHubUserFooterStateAdapter
import com.example.github.ui.viewmodel.UserListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserListFragment : BaseFragment() {
    private lateinit var binding: FragmentGithubUsersBinding
    private val viewModel: UserListViewModel by viewModels()
    private lateinit var userAdapter: GitHubUserAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGithubUsersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userAdapter = GitHubUserAdapter()
        binding.userList.adapter = userAdapter.withLoadStateFooter(GitHubUserFooterStateAdapter {
            userAdapter.retry()
        })
        userAdapter.addLoadStateListener {
            when (it.source.refresh) {
                is LoadState.Loading -> {
                    mainActivity.showLoading()
                }
                is LoadState.NotLoading -> {
                    mainActivity.dismissLoading()
                }
                is LoadState.Error -> {
                    mainActivity.dismissLoading()
                    mainActivity.showError((it.source.refresh as LoadState.Error).error.message)
                }
            }
        }
        viewModel.githubUser.observe(viewLifecycleOwner) {
            userAdapter.submitData(lifecycle, it)
        }
    }

}