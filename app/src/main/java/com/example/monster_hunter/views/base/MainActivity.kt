package com.example.monster_hunter.views.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.example.monster_hunter.R
import com.example.monster_hunter.databinding.ActivityMainBinding
import com.example.monster_hunter.utils.AppTabItem
import com.example.monster_hunter.utils.tabLayoutBuilder
import com.example.monster_hunter.views.about.AboutFragment
import com.example.monster_hunter.views.home.HomeFragment

class MainActivity() : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setTheme(R.style.Theme_Monsterhunter)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(view)

        with(binding) {
            tabsViewPager.tabLayoutBuilder(
                tabLayout, supportFragmentManager, listOf(
                    AppTabItem("Armors", HomeFragment()),
                    AppTabItem("About", AboutFragment())
                    )
            )
        }
    }


}