package com.example.github.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.github.R
import com.example.github.base.BaseFragment
import com.example.github.base.BaseViewModel
import com.example.github.databinding.FragmentGithubUsersBinding
import com.example.github.networkutils.LoadingStatus
import com.example.github.ui.adapter.GitHubUserAdapter
import com.example.github.ui.adapter.GitHubUserFooterStateAdapter
import com.example.github.ui.viewmodel.UserListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserListFragment : BaseFragment() {
    private lateinit var binding: FragmentGithubUsersBinding
    private val viewModel: UserListViewModel by viewModels()
    private lateinit var userAdapter: GitHubUserAdapter


    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

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
        viewModel.fetchGithubUsers()
        userAdapter = GitHubUserAdapter(
            addToFavorite = {
                viewModel.saveUser(it)
            }, viewMoreDetails = {
                findNavController().navigate(UserListFragmentDirections.actionUserListFragmentToUserDetailsFragment(it))
            }
        )
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

        observeViewModel()
    }


    private fun observeViewModel() {
        viewModel.apply {
            githubUser.observe(viewLifecycleOwner) {
                userAdapter.submitData(lifecycle, it)
            }
            status.observe(viewLifecycleOwner){
                when(it){
                    is LoadingStatus.Success -> {
                        showSnackBar(getString(R.string.added_to_favourites))
                        getViewModel().errorShown()
                    }
                    is LoadingStatus.Error -> {
                        mainActivity.showError(it.errorMessage)
                    }
                }
            }
        }
    }

}