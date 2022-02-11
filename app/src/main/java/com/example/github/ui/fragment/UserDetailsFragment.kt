package com.example.github.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.github.R
import com.example.github.base.BaseFragment
import com.example.github.base.BaseViewModel
import com.example.github.databinding.FragmentUserDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailsFragment : Fragment() {
    private lateinit var binding : FragmentUserDetailsBinding
    private val args : UserDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val githubUser = args.githubUser
        binding.apply {
            profilePicture.load(githubUser.avatarUrl)
            usernameTextView.text = githubUser.login
            profileUrl.text = getString(R.string.github_url_format).format(githubUser.url)
            htmlUrl.text = getString(R.string.html_url_format).format(githubUser.htmlUrl)
            followersUrl.text = getString(R.string.followers_url).format(githubUser.followersUrl)
        }
    }

}