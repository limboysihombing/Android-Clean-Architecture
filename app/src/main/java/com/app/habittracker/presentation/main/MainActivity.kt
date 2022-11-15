package com.app.habittracker.presentation.main

import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.app.habittracker.R
import com.app.habittracker.databinding.ActivityMainBinding
import com.app.habittracker.presentation.HistoryFragment
import com.app.habittracker.presentation.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= 21) {
            val window: Window = this.getWindow()

            // clear FLAG_TRANSLUCENT_STATUS flag:
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

            // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

            // finally change the color
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.white))
        }


        var fragment : Fragment = HomeFragment()
        supportFragmentManager.beginTransaction()
            .replace(binding.frameContainer.id, fragment, fragment.javaClass.simpleName)
            .commit()

        binding.bottomNavMenu.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.nav_home -> {
                    fragment = HomeFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(binding.frameContainer.id, fragment, fragment.javaClass.simpleName)
                        .commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.nav_history -> {
                    fragment = HistoryFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(binding.frameContainer.id, fragment, fragment.javaClass.simpleName)
                        .commit()
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
    }

}