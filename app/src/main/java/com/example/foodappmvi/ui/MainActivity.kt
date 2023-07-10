package com.example.foodappmvi.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.foodappmvi.R
import com.example.foodappmvi.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navHost: NavHostFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        main()
    }

    private fun main() {
        binding.apply {

            navHost = supportFragmentManager.findFragmentById(R.id.nav_main) as NavHostFragment
            //set bottom to main
            bottomNavigationView.setupWithNavController(navHost.navController)

            // check bottom is show
            navHost.navController.addOnDestinationChangedListener { _, d, _ ->
                when (d.id) {
                    R.id.detailFragment -> { bottomNavigationView.isVisible = false }
                    else -> { bottomNavigationView.isVisible = true }
                }
            }

            //navHost.navController.addOnDestinationChangedListener { controller, destination, arguments -> }

        }
    }

    override fun onNavigateUp(): Boolean {
        return navHost.navController.navigateUp() || super.onNavigateUp()
    }
}