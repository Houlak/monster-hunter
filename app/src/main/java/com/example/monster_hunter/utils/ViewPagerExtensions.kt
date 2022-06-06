package com.example.monster_hunter.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout


fun ViewPager.tabLayoutBuilder(tabLayout: TabLayout, fragmentManager: FragmentManager, list: List<AppTabItem>){
    val viewpager = this
    list.forEach {
        tabLayout.addTab(tabLayout.newTab())
    }
    val adapter = HomeTabsAdapter(list, fragmentManager, tabLayout.tabCount)
    viewpager.adapter = adapter

    viewpager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

    tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab) {
            viewpager.currentItem = tab.position
        }
        override fun onTabUnselected(tab: TabLayout.Tab) {}
        override fun onTabReselected(tab: TabLayout.Tab) {}
    })

}

class HomeTabsAdapter(
    val list: List<AppTabItem>,
    fm: FragmentManager,
    var totalTabs: Int,
) : FragmentStatePagerAdapter(fm) {

    override fun getPageTitle(position: Int): CharSequence? {
        return list[position].title
    }

    override fun getItem(position: Int): Fragment {
        return list[position].fragment
    }

    override fun getCount(): Int {
        return totalTabs
    }
}

data class AppTabItem(val title: String, val fragment: Fragment)