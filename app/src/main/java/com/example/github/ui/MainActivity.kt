package com.example.github.ui

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.github.R
import com.example.github.base.BaseActivity
import com.example.github.databinding.ActivityMainBinding
import com.example.github.extensions.setVisibilityState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    private val bottomNavVisibleDestinations = setOf(
        R.id.favouriteFragment,
        R.id.userListFragment
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNav.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bottomNav.setVisibilityState(bottomNavVisibleDestinations.contains(destination.id))
        }
    }

    override fun dismissLoading() = binding.progressIndicator.hide()

    override fun isLoading(): Boolean = binding.progressIndicator.isVisible

    override fun showLoading(message: String) = binding.progressIndicator.show()


}