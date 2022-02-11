package com.example.github.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.github.R
import com.example.github.base.BaseFragment
import com.example.github.base.BaseViewModel
import com.example.github.databinding.FragmentSavedUserBinding
import com.example.github.networkutils.LoadingStatus
import com.example.github.ui.adapter.GithubSaveUserAdapter
import com.example.github.ui.viewmodel.FavouriteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteFragment : BaseFragment() {
    private val viewModel: FavouriteViewModel by viewModels()
    private lateinit var binding : FragmentSavedUserBinding
    private lateinit var adapter : GithubSaveUserAdapter

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_favourite, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete_all_user) {
            viewModel.deleteAllUser()
            return true
        }
        return false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSavedUserBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = GithubSaveUserAdapter(
            deleteFavorite = {
                viewModel.deleteUser(it.login)
            },
            viewMoreDetails = {
                findNavController().navigate(FavouriteFragmentDirections.actionFavouriteFragmentToUserDetailsFragment(it))
            }
        )
        binding.userList.adapter = adapter
        observeViewModel()
    }


    private fun observeViewModel(){
        viewModel.apply {
            githubUser.observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }
        }
    }
}